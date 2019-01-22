package com.transport.travelmanager.controllers.restful;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transport.travelmanager.domain.Person;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.services.PersonService;
import com.transport.travelmanager.utils.CreatEntityRandomDataHelper;
import com.transport.travelmanager.utils.JackJsonUtils;
@RunWith(MockitoJUnitRunner.class)
public class PersonControllerRestfulTest {
	@InjectMocks
	private PersonControllerRestful controller;
	
	@Mock
	private PersonService service; 
	
	private static Person person;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		person = CreatEntityRandomDataHelper.getRandomPerson();
	}

	@Test
	public void testAddPersonRequestHasNoName() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("socialId", person.getSocialId());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}

	@Test
	public void testAddPersonRequestHasNameEmpty() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("name", new String());
		request.put("socialId", person.getSocialId());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddPersonRequestHasNoSocialId() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("name", person.getName());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}

	@Test
	public void testAddPersonRequestHasSocialIdEmpty() throws JsonProcessingException, ParseException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("name", person.getName());
		request.put("socialId", new String());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddPersonRequestSocialIdAlredyExist() throws JsonProcessingException, ParseException, TravelManagerException {
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("name", person.getName());
		request.put("socialId", person.getSocialId());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "The social ID alredy exist");
		when(service.save(person)).thenThrow(new TravelManagerException("The social ID alredy exist"));
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testAddPersonHasBirthDate() throws JsonProcessingException, ParseException, TravelManagerException {
		ObjectNode response = JackJsonUtils.createNewNode();
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("name", person.getName());
		request.put("socialId",  person.getSocialId());
		request.put("birthDate", "2018-03-09T14:49:36");
		response.setAll(JackJsonUtils.convertValue(person));
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.CREATED).body(response);
		when(service.save(Mockito.any(Person.class))).thenReturn(person);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}	
	@Test
	public void testAddPersonHasBirthDateIsEmpty() throws JsonProcessingException, ParseException, TravelManagerException {
		ObjectNode response = JackJsonUtils.createNewNode();
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("name", person.getName());
		request.put("socialId",  person.getSocialId());
		request.put("birthDate", "");
		response.setAll(JackJsonUtils.convertValue(person));
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.CREATED).body(response);
		when(service.save(Mockito.any(Person.class))).thenReturn(person);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}	
	@Test
	public void testAddPerson() throws JsonProcessingException, ParseException, TravelManagerException {
		ObjectNode response = JackJsonUtils.createNewNode();
		ObjectNode request = JackJsonUtils.createNewNode();
		request.put("name", person.getName());
		request.put("socialId",  person.getSocialId());
		response.setAll(JackJsonUtils.convertValue(person));
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.CREATED).body(response);
		when(service.save(Mockito.any(Person.class))).thenReturn(person);
		ResponseEntity<JsonNode> responseEntityGotten = controller.addPerson(request);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	
	@Test
	public void testGetPersonBySocialIdHasNoSocialId() throws IOException {
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");	
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.getPersonBySocialId(null);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testGetPersonBySocialIdHasSocialIdEmpty() throws IOException {
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");	
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.getPersonBySocialId(new String());
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testGetPersonBySocialId() throws IOException {
		when(service.findBySocialId(Mockito.anyString())).thenReturn(person);
		ObjectNode response = JackJsonUtils.createNewNode();
		response.setAll(JackJsonUtils.convertValue(person));
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.OK).body(response);
		ResponseEntity<JsonNode> responseEntityGotten = controller.getPersonBySocialId(person.getSocialId());
		assertNotNull(response);
		assertEquals(HttpStatus.OK, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}	
	@Test
	public void testGetPeopleByNameWithNameIsEmpty() {
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ArrayNode a = new ArrayNode(JsonNodeFactory.instance);
		a.add(response);
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(a);
		ResponseEntity<ArrayNode> responseEntityGotten = controller.getPeopleByName(new String());
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testGetPeopleByNameWithNameNull() {
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "Wrong attributes sent");
		ArrayNode a = new ArrayNode(JsonNodeFactory.instance);
		a.add(response);
		ResponseEntity<JsonNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(a);
		ResponseEntity<ArrayNode> responseEntityGotten = controller.getPeopleByName(null);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testGetPeopleByName() {
		List<Person> lPerson = new ArrayList<>();
		lPerson.add(person);
		Person person2 = CreatEntityRandomDataHelper.getRandomPerson();
		person2.setName(person.getName());
		lPerson.add(person2);
		Person person3 = CreatEntityRandomDataHelper.getRandomPerson();
		person3.setName(person.getName());
		lPerson.add(person3);
		when(service.findByName(Mockito.anyString())).thenReturn(lPerson);
		ArrayNode response = JackJsonUtils.listObjectToJsonNode(lPerson);
		ResponseEntity<ArrayNode> responseEntityExcepted =  ResponseEntity.status(HttpStatus.OK).body(response);
		ResponseEntity<ArrayNode> responseEntityGotten = controller.getPeopleByName((person.getName()));
		assertNotNull(response);
		assertEquals(HttpStatus.OK, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testUpdatePersonBySocialId() throws IOException, ParseException {
		String patchOp="[{\"op\": \"remove\", \"path\": \"/birthDate\" },{ \"op\": \"add\", \"path\": \"/birthDate\", \"value\": \"2018-03-09T14:49:36\" },{ \"op\": \"replace\", \"path\": \"/birthDate\", \"value\": \"1980-06-23T00:00:00\" },{ \"op\": \"test\", \"path\": \"/birthDate\", \"value\": \"1980-06-23T00:00:00\"}]";
		JsonNode request = JackJsonUtils.readTree(patchOp);
		person.setBirthDate(new Date());
		Person person2 = new Person();
		person2.setId(person.getId());
		person2.setName(person.getName());
		person2.setSocialId(person.getSocialId());
		person2.setBirthDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("1980-06-23T00:00:00"));
		when(service.findBySocialId(person.getSocialId())).thenReturn(person);
		String personString = JackJsonUtils.entityToJsonString(person2);
		ResponseEntity<String> responseEntityExcepted =  ResponseEntity.status(HttpStatus.OK).body(personString);
		ResponseEntity<String> responseEntityGotten = controller.updatePersonBySocialId(person.getSocialId(),request);
		assertNotNull(responseEntityExcepted);
		assertEquals(HttpStatus.OK, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}
	@Test
	public void testUpdatePersonBySocialIdException() throws IOException {
		String patchOp="[{\"op\": \"rr\", \"path\": \"/birthDate\"}]";
		JsonNode request = JackJsonUtils.readTree(patchOp);
		person.setBirthDate(new Date());
		ObjectNode response = JackJsonUtils.createNewNode();
		response.put("code","400");
		response.put("error", "Bad Request");
		response.put("cause", "The Patch Operation rr isn't valid");
		when(service.findBySocialId(person.getSocialId())).thenReturn(person);
		ResponseEntity<String> responseEntityExcepted =  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JackJsonUtils.entityToJsonString(response));
		ResponseEntity<String> responseEntityGotten = controller.updatePersonBySocialId(person.getSocialId(),request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntityGotten.getStatusCode());
		assertEquals(responseEntityExcepted.getBody(), responseEntityGotten.getBody());	
	}

}
