package com.transport.travelmanager.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import com.transport.travelmanager.controllers.PersonController;
import com.transport.travelmanager.domain.Person;
import com.transport.travelmanager.domain.dtos.PersonDTO;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.repositories.PersonRepository;
import com.transport.travelmanager.utils.CreatEntityRandomDataHelper;
import com.transport.travelmanager.utils.GeraCpfCnpj;
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
	public void testCreateNewPersonNameAttribEmpty() throws TravelManagerException, IOException, ParseException {
		Person person = CreatEntityRandomDataHelper.getRandomPerson();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",new String());
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
	public void testCreateNewPersonSocialIdAttribEmpty() throws TravelManagerException, IOException, ParseException {
		Person person = CreatEntityRandomDataHelper.getRandomPerson();
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",person.getName());
		fakeNodeRequest.put("socialId", new String());
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
	@Test
	public void testGetPersonBySocialIdNull() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		ResponseEntity<JsonNode> response = controller.getPersonBySocialId(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent");
		ObjectNode node = JackJsonUtils.createNewNode();
		node.put("socialId", "");
		responseNode.set("Person Attribute Needed", node);
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testGetPersonBySocialIdEmpty() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("socialId",new String());
		ResponseEntity<JsonNode> response = controller.getPersonBySocialId(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent");
		ObjectNode node = JackJsonUtils.createNewNode();
		node.put("socialId", "");
		responseNode.set("Person Attribute Needed", node);
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testGetPeopleByName() throws TravelManagerException, IOException, ParseException {
		Person person1 = CreatEntityRandomDataHelper.getRandomPerson();
		List<Person> lPerson = new ArrayList<>();
		for(long i =0L;i<5L;i++) {
			Person person = new Person(person1.getId()+i,person1.getName(),GeraCpfCnpj.cpf());
			lPerson.add(person);
		}
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",person1.getName());
		when(repository.findByName(Mockito.anyString())).thenReturn(lPerson);
		ResponseEntity<JsonNode> response = controller.getPeopleByName(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.putArray("People").addAll(JackJsonUtils.listObjectToJsonNode(lPerson));
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.OK).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testGetPeopleByNameNull() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		ResponseEntity<JsonNode> response = controller.getPeopleByName(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent to consult");
		ObjectNode node = JackJsonUtils.createNewNode();
		node.put("name", "");
		responseNode.set("Person Attribute Needed", node);
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
	@Test
	public void testGetPeopleByNameEmpty() throws TravelManagerException, IOException, ParseException {
		ObjectNode fakeNodeRequest = JackJsonUtils.createNewNode();
		fakeNodeRequest.put("name",new String());
		ResponseEntity<JsonNode> response = controller.getPeopleByName(fakeNodeRequest);
		ObjectNode responseNode = JackJsonUtils.createNewNode();
		responseNode.put("message","Wrong attributes sent to consult");
		ObjectNode node = JackJsonUtils.createNewNode();
		node.put("name", "");
		responseNode.set("Person Attribute Needed", node);
		assertNotNull(response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseNode).getBody(), response.getBody());
	}
}
