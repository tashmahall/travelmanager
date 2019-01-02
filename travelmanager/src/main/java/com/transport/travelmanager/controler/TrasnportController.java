package com.transport.travelmanager.controler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.services.TransportService;
import com.transport.travelmanager.utils.JackJsonUtils;

@RestController
public class TrasnportController {
	
	@Autowired
	private TransportService service;
	
	@PostMapping(path="/newtransport",consumes= {MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode> createNewTransport(@RequestBody JsonNode request) throws IOException, ParseException, TravelManagerException {
		Destiny destiny = new Destiny();
		Long destinyId= request.get("destinyId").asLong();
		destiny.setId(destinyId);
		Long vehcileId = request.get("vehicleId").asLong();
		Vehicle vehicle = new Vehicle();
		vehicle.setId(vehcileId);
		String dateTravelStartString = request.get("dateTimeTravelStart").asText();
		Date dateTraveStart = parseJsonDateToJavaDate(dateTravelStartString);
		Transport responseEntity = service.createNewTransport(destiny, vehicle, dateTraveStart);
		ObjectNode response = JackJsonUtils.convertValue(responseEntity);
		response.put("message", "Transport created with success");
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
    private Date parseJsonDateToJavaDate( String input ) throws java.text.ParseException {

        //NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
        //things a bit.  Before we go on we have to repair this.
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        
        //this is zero time so we need to add that TZ indicator for 
        if ( input.endsWith( "Z" ) ) {
            input = input.substring( 0, input.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;
        
            String s0 = input.substring( 0, input.length() - inset );
            String s1 = input.substring( input.length() - inset, input.length() );

            input = s0 + "GMT" + s1;
        }
        
        return df.parse( input );
        
    }

    private String toString( Date date ) {
        
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
        
        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        
        df.setTimeZone( tz );

        String output = df.format( date );

        int inset0 = 9;
        int inset1 = 6;
        
        String s0 = output.substring( 0, output.length() - inset0 );
        String s1 = output.substring( output.length() - inset1, output.length() );

        String result = s0 + s1;

        result = result.replaceAll( "UTC", "+00:00" );
        
        return result;
        
    }
}
