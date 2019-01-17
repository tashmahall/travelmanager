package com.transport.travelmanager.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;

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
import com.transport.travelmanager.controllers.DestinyController;
import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.dtos.DestinyDTO;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.repositories.DestinyRepository;
import com.transport.travelmanager.utils.CreatEntityRandomDataHelper;
import com.transport.travelmanager.utils.JackJsonUtils;

@RunWith(MockitoJUnitRunner.class)
public class DestinyControllerTest {
	@InjectMocks
	private DestinyController controller;
	@Mock
	private DestinyRepository repository;
	
	@Test
	public void testAddNewDestiny() throws TravelManagerException, IOException, ParseException {
		Destiny destiny = CreatEntityRandomDataHelper.getRandomDestiny();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",destiny.getName());
		when(repository.save(Mockito.any(Destiny.class))).thenReturn(destiny);
		ResponseEntity<JsonNode> response = controller.addDestiny(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.set("Destiny", JackJsonUtils.convertValue(destiny));
		responseNode.put("message", "Destiny created with success");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testCreateNewDestinationNameAttribNull() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		ResponseEntity<JsonNode> response = controller.addDestiny(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent");
		responseNode.set("Destiny Attributes",JackJsonUtils.convertValue(new DestinyDTO("")));
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testCreateNewDestinationNameAttribEmpty() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",new String());
		ResponseEntity<JsonNode> response = controller.addDestiny(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent");
		responseNode.set("Destiny Attributes",JackJsonUtils.convertValue(new DestinyDTO("")));
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testGetDestinyByName() throws TravelManagerException, IOException, ParseException {
		Destiny destiny = CreatEntityRandomDataHelper.getRandomDestiny();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",destiny.getName());
		when(repository.findByName(Mockito.anyString())).thenReturn(destiny);
		ResponseEntity<JsonNode> response = controller.getDestinyByName(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.set("Destiny", JackJsonUtils.convertValue(destiny));
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testGetDestinyByNamedNull() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		ResponseEntity<JsonNode> response = controller.getDestinyByName(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent to consult");
		ObjectNode node = JackJsonUtils.createNewNode();
		node.put("name", "");
		responseNode.set("Destiny Attributes Needed", node);
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testGetDestinyByNameEmpty() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",new String());
		ResponseEntity<JsonNode> response = controller.getDestinyByName(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent to consult");
		ObjectNode node = JackJsonUtils.createNewNode();
		node.put("name", "");
		responseNode.set("Destiny Attributes Needed", node);
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
}
