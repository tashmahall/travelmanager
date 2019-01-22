package com.transport.travelmanager.controllers.restful;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.services.PersonService;
import com.transport.travelmanager.utils.JackJsonUtils;
import com.transport.travelmanager.utils.PatchActivityUtils;

@RestController
public class PersonControllerRestful {
	@Autowired
	private PersonService service;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	@PostMapping(path="/people",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode> addPerson(@RequestBody JsonNode request) throws JsonProcessingException, ParseException {
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name")||request.get("name").asText().isEmpty()||!request.has("socialId")||request.get("socialId").asText().isEmpty()){
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", "Wrong attributes sent");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Person person = new Person();
		person.setName(request.get("name").textValue().toUpperCase());
		person.setSocialId(request.get("socialId").textValue().toUpperCase());
		if(request.has("birthDate") &&!request.get("birthDate").textValue().isEmpty()) {
			String dateString = request.get("birthDate").textValue();
			Date birthDate = sdf.parse(dateString);
			person.setBirthDate(birthDate);
		}
		try {
			person = service.save(person);
			response.setAll(JackJsonUtils.convertValue(person));
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (TravelManagerException e) {
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}
	@GetMapping(path="/people/{socialId}",produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<JsonNode>getPersonBySocialId(@PathVariable(name="socialId") String socialId) throws IOException{
		ObjectNode response = JackJsonUtils.createNewNode();
		if(socialId == null||socialId.isEmpty()){
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", "Wrong attributes sent");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Person person = service.findBySocialId(socialId);
		response = JackJsonUtils.convertValue(person);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@GetMapping(path="/people",produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ArrayNode>getPeopleByName(@RequestParam(name="name") String name){
		ObjectNode response = JackJsonUtils.createNewNode();
		if(name==null||name.isEmpty()){
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", "Wrong attributes sent");
			ArrayNode a = new ArrayNode(JsonNodeFactory.instance);
			a.add(response);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(a);
		}
		List<Person> lPeople = service.findByName(name);
		return ResponseEntity.status(HttpStatus.OK).body(JackJsonUtils.listObjectToJsonNode(lPeople));
	}
	@PatchMapping(path="/people/{socialId}",produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<String>updatePersonBySocialId(@PathVariable(name="socialId") String socialId,@RequestBody JsonNode request) throws IOException{
		Person person = service.findBySocialId(socialId);
		Long id = person.getId();
		String personString = JackJsonUtils.entityToJsonString(person);
		Iterator<JsonNode> iteratorPatchOp = request.elements();
		try {
			while(iteratorPatchOp.hasNext()) {
				JsonNode jsonNodePatchOp = iteratorPatchOp.next();
				personString = PatchActivityUtils.patchActivityString(jsonNodePatchOp, personString);
			}
			person = JackJsonUtils.jsonStringToObject(personString, Person.class);
			person.setSocialId(socialId);
			person.setId(id);
			service.save(person);
			return ResponseEntity.status(HttpStatus.OK).body(personString);
		}catch(TravelManagerException tme) {
			ObjectNode response = JackJsonUtils.createNewNode();
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", tme.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
	}
}
