package com.transport.travelmanager.services;

import static com.transport.travelmanager.utils.CreatEntityRandomDataHelper.getNewTransport;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.repositories.DestinyRepository;
import com.transport.travelmanager.repositories.TransportRepository;
import com.transport.travelmanager.repositories.VehicleRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceTests {
	@InjectMocks
	private TransportService transportService;
	@Mock(stubOnly=false)
	private TransportRepository transportRepository;
	@Mock(stubOnly=false)
	private VehicleRepository vehicleRepository;
	@Mock(stubOnly=false)
	private DestinyRepository destinyRepository;
	@Mock
	private JdbcTemplate JdbcTemplate;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCreateNewTransport() throws TravelManagerException {
		Transport transport = getNewTransport();
		Optional<Destiny> oDestiny = Optional.of(transport.getDestiny());
		Optional<Vehicle> oVehicle = Optional.of(transport.getVehicle());
		
		when(destinyRepository.findById(anyLong())).thenReturn(oDestiny);
		when(vehicleRepository.findById(anyLong())).thenReturn(oVehicle);
		when(transportRepository.createNewTransport(transport.getDestiny().getId(), transport.getVehicle().getId(),transport.getVehicle().getCapacity(), transport.getDateTimeTravelStart(),transport.getTransportCode())).thenReturn(transport);
		
		Transport tTemp = transportService.createNewTransport(transport.getDestiny(), transport.getVehicle(), transport.getDateTimeTravelStart(),transport.getTransportCode());
		
		assertEquals("The Transport received was different than the expected",transport,tTemp);
	}
	
	@Test 
	public void testCreateNewTransportDestinyNull() throws TravelManagerException {
		Transport transport = getNewTransport();
	
	    thrown.expect(TravelManagerException.class);
	    thrown.expectMessage("The destiny, vehicle and date time to travel Start must be informed");
		transportService.createNewTransport(null, transport.getVehicle(), transport.getDateTimeTravelStart(),transport.getTransportCode());	
	}
	@Test 
	public void testCreateNewTransportVehicleNull() throws TravelManagerException {
		Transport transport = getNewTransport();
	
	    thrown.expect(TravelManagerException.class);
	    thrown.expectMessage("The destiny, vehicle and date time to travel Start must be informed");
		transportService.createNewTransport(transport.getDestiny(), null, transport.getDateTimeTravelStart(),transport.getTransportCode());	
	}
	@Test 
	public void testCreateNewTransportDateTravelStartNull() throws TravelManagerException {
		Transport transport = getNewTransport();
	
	    thrown.expect(TravelManagerException.class);
	    thrown.expectMessage("The destiny, vehicle and date time to travel Start must be informed");
		transportService.createNewTransport(transport.getDestiny(), transport.getVehicle(), null,transport.getTransportCode());	
	}

}

