package com.transport.travelmanager.controllers.restful;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.services.VehicleService;
import com.transport.travelmanager.utils.CreatEntityRandomDataHelper;
import com.transport.travelmanager.utils.JackJsonUtils;
@RunWith(MockitoJUnitRunner.class)
public class VehicleControllerRestfulTest {
	@InjectMocks
	private VehicleControllerRestful controller;
	
	@Mock
	private VehicleService service; 
	
	private static Vehicle vehicle;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vehicle = CreatEntityRandomDataHelper.getRandomVehicle();
	}

	@Test
	public void testAddVehicleRequestHasNoVehicleCode() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}

	@Test
	public void testAddVehicleRequestHasVehicleCodeEmpty() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("vehicleCode", new String());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddVehicleRequestHasNoName() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("vehicleCode", vehicle.getVehicleCode());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}

	@Test
	public void testAddVehicleRequestHasNameEmpty() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("vehicleCode", vehicle.getVehicleCode());
		request.put("name", new String());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddVehicleRequestHasNoCapacity() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("vehicleCode", vehicle.getVehicleCode());
		request.put("name", vehicle.getName());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddVehicleRequestHasCapacityEmpty() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("vehicleCode", vehicle.getVehicleCode());
		request.put("name", vehicle.getName());
		request.put("capacity", 0);
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddVehicleExceptionVehicleCodeAlredyExist() throws JsonProcessingException, ParseException, TravelManagerException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("vehicleCode", vehicle.getVehicleCode());
		request.put("name", vehicle.getName());
		request.put("capacity", vehicle.getCapacity());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "The vehicle code alredy exist in the Database.");
		when(service.save(vehicle)).thenThrow(new TravelManagerException("The vehicle code alredy exist in the Database."));
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddVehicleRequest() throws JsonProcessingException, ParseException, TravelManagerException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("vehicleCode", vehicle.getVehicleCode());
		request.put("name", vehicle.getName());
		request.put("capacity", vehicle.getCapacity());
		ObjectNode response = JackJsonUtils.convertValue(vehicle);
		when(service.save(vehicle)).thenReturn(vehicle);
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.CREATED).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addVehicle(request);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testfindVehicleByVehicleCodee() throws JsonProcessingException, ParseException, TravelManagerException {
		ObjectNode response = JackJsonUtils.convertValue(vehicle);
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.OK).body(response);
		when(service.findByVehicleCode(vehicle.getVehicleCode())).thenReturn(vehicle);
		ResponseEntity<JsonNode> responseEntityGotten = controller.findVehicleByVehicleCode(vehicle.getVehicleCode());
		assertNotNull(response);
		assertEquals(HttpStatus.OK, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}	
}
