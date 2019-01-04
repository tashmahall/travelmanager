package com.transport.travelmanager.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transport.travelmanager.controler.TransportController;
import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.services.TransportService;
import com.transport.travelmanager.utils.CreatEntityRandomDataHelper;
import com.transport.travelmanager.utils.JackJsonUtils;

@RunWith(MockitoJUnitRunner.class)
public class TransportControllerTest {
	@InjectMocks
	private TransportController transportController;
	@Mock
	private TransportService transportService;
	
	private SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
	@Test
	public void testCreateNewTransport() throws TravelManagerException, IOException, ParseException {
		Transport transport = CreatEntityRandomDataHelper.getNewTransport();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("destinyId",transport.getDestiny().getId());
		fakeNodeRequest.put("vehicleId",transport.getVehicle().getId());
		fakeNodeRequest.put("dateTimeTravelStart", df.format(transport.getDateTimeTravelStart()));
		when(transportService.createNewTransport(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(transport);
		ResponseEntity<JsonNode> response = transportController.createNewTransport(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.set("Transport", JackJsonUtils.convertValue(transport));
		responseNode.put("message", "Transport created with success");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(responseNode).getBody(), response.getBody());
		
	}

}
