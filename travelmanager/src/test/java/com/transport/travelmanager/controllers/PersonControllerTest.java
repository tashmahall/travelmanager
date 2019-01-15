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
import com.transport.travelmanager.controler.PersonController;
import com.transport.travelmanager.domain.Person;
import com.transport.travelmanager.domain.dtos.PersonDTO;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.repository.PersonRepository;
import com.transport.travelmanager.utils.CreatEntityRandomDataHelper;
import com.transport.travelmanager.utils.JackJsonUtils;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
	@InjectMocks
	private PersonController controller;
	@Mock
	private PersonRepository repository;
	
	@Test
	public void testCreateNewPerson() throws TravelManagerException, IOException, ParseException {
		Person person = CreatEntityRandomDataHelper.getRandomPerson();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",person.getName());
		fakeNodeRequest.put("socialId",person.getSocialId());
		when(repository.save(Mockito.any(Person.class))).thenReturn(person);
		ResponseEntity<JsonNode> response = controller.addPerson(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.set("Person", JackJsonUtils.convertValue(person));
		responseNode.put("message",  "Person created with success");
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(responseNode).getBody(), response.getBody());
		
	}
	@Test
	public void testCreateNewPersonNameAttribNull() throws TravelManagerException, IOException, ParseException {
		Person person = CreatEntityRandomDataHelper.getRandomPerson();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("socialId",person.getSocialId());
		ResponseEntity<JsonNode> response = controller.addPerson(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent");
		responseNode.set("Person Attributes",JackJsonUtils.convertValue(new PersonDTO("","")));
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
		
	}
	@Test
	public void testCreateNewPersonSocialIdAttribNull() throws TravelManagerException, IOException, ParseException {
		Person person = CreatEntityRandomDataHelper.getRandomPerson();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",person.getName());
		ResponseEntity<JsonNode> response = controller.addPerson(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent");
		responseNode.set("Person Attributes",JackJsonUtils.convertValue(new PersonDTO("","")));
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
		
	}		
	@Test
	public void testGetPersonBySocialId() throws TravelManagerException, IOException, ParseException {
		Person person = CreatEntityRandomDataHelper.getRandomPerson();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",person.getName());
		fakeNodeRequest.put("socialId",person.getSocialId());
		when(repository.findBySocialId(Mockito.anyString())).thenReturn(person);
		ResponseEntity<JsonNode> response = controller.getPersonBySocialId(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.set("Person", JackJsonUtils.convertValue(person));
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(responseNode).getBody(), response.getBody());
		
	}

}
