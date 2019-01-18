package com.transport.travelmanager.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transport.travelmanager.domain.Person;
import com.transport.travelmanager.domain.dtos.PersonDTO;
import com.transport.travelmanager.repositories.PersonRepository;
import com.transport.travelmanager.utils.JackJsonUtils;

@RestController
public class PersonController {
	@Autowired
	private PersonRepository repository;
	
	@PostMapping(path="/addperson",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode> addPerson(@RequestBody JsonNode request) throws JsonProcessingException {
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name")||request.get("name").asText().isEmpty()||!request.has("socialId")||request.get("socialId").asText().isEmpty()){
			response.put("message","Wrong attributes sent");
			response.set("Person Attributes",JackJsonUtils.convertValue(new PersonDTO("","", "yyyy-MM-dd")));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		Person person = new Person();
		person.setName(request.get("name").textValue());
		person.setSocialId(request.get("socialId").textValue());
		person = repository.save(person);
		response.put("message", "Person created with success");
		response.set("Person",JackJsonUtils.convertValue(person));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping(path="/getpersonbysocialid",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode>getPersonBySocialId(@RequestBody JsonNode request) throws IOException{
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("socialId")||request.get("socialId").asText().isEmpty()){
			response.put("message","Wrong attributes sent");
			ObjectNode node = JackJsonUtils.createNewNode();
			node.put("socialId", "");
			response.set("Person Attribute Needed", node);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		String socialId = request.get("socialId").textValue();
		Person person = repository.findBySocialId(socialId);
		response.set("Person",JackJsonUtils.convertValue(person));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping(path="/getpeoplebyname",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode>getPeopleByName(@RequestBody JsonNode request){
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name")||request.get("name").asText().isEmpty()){
			response.put("message","Wrong attributes sent to consult");
			ObjectNode node = JackJsonUtils.createNewNode();
			node.put("name", "");
			response.set("Person Attribute Needed", node);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		String name = request.get("name").textValue();
		List<Person> lPeople = repository.findByName(name);
		response.putArray("People").addAll(JackJsonUtils.listObjectToJsonNode(lPeople));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
