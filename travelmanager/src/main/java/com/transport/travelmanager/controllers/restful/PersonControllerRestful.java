package com.transport.travelmanager.controllers.restful;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transport.travelmanager.domain.Person;
import com.transport.travelmanager.domain.dtos.PersonDTO;
import com.transport.travelmanager.repositories.PersonRepository;
import com.transport.travelmanager.utils.JackJsonUtils;

@RestController
public class PersonControllerRestful {
	@Autowired
	private PersonRepository repository;
	
	@PostMapping(path="/people",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode> addPerson(@RequestBody JsonNode request) throws JsonProcessingException {
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name")||request.get("name").asText().isEmpty()||!request.has("socialId")||request.get("socialId").asText().isEmpty()){
			response.put("message","Wrong attributes sent");
			response.set("Person Attributes",JackJsonUtils.convertValue(new PersonDTO("","")));
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
	@GetMapping(path="/people/{socialId}",produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<JsonNode>getPersonBySocialId(@PathVariable(name="socialId") String socialId) throws IOException{
		ObjectNode response = JackJsonUtils.createNewNode();
		if(socialId == null||socialId.isEmpty()){
			response.put("message","Wrong attributes sent");
			ObjectNode node = JackJsonUtils.createNewNode();
			node.put("socialId", "");
			response.set("Person Attribute Needed", node);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		Person person = repository.findBySocialId(socialId);
		response = JackJsonUtils.convertValue(person);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping(path="/people",produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ArrayNode>getPeopleByName(@RequestParam(name="name") String name){
		ObjectNode response = JackJsonUtils.createNewNode();
		if(name==null||name.isEmpty()){
			response.put("message","Wrong attributes sent to consult");
			ObjectNode node = JackJsonUtils.createNewNode();
			node.put("name", "");
			response.set("Person Attribute Needed", node);
			ArrayNode a = new ArrayNode(JsonNodeFactory.instance);
			a.add(node);
			a.add(response);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(a);
		}
		List<Person> lPeople = repository.findByName(name);
		return ResponseEntity.status(HttpStatus.OK).body(JackJsonUtils.listObjectToJsonNode(lPeople));
	}
//	@PatchMapping(path="/people/{socialId}",produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public ResponseEntity<JsonNode>updatePersonBySocialId(@PathVariable(name="socialId") String socialId, @RequestParam String name) throws IOException{
//		ObjectNode response = JackJsonUtils.createNewNode();
//		if(socialId == null||socialId.isEmpty()){
//			response.put("message","Wrong attributes sent");
//			ObjectNode node = JackJsonUtils.createNewNode();
//			node.put("socialId", "");
//			response.set("Person Attribute Needed", node);
//			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
//		}
//		Person person = repository.findBySocialId(socialId);
//		response = JackJsonUtils.convertValue(person);
//		return ResponseEntity.status(HttpStatus.OK).body(response);
//	}
}
