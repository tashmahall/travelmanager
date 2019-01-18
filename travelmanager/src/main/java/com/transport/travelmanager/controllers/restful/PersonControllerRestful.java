package com.transport.travelmanager.controllers.restful;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonPatch;
import javax.json.JsonPatchBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;

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
import com.transport.travelmanager.domain.dtos.PersonDTO;
import com.transport.travelmanager.exceptions.TravelManagerException;
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
			response.set("Person Attributes",JackJsonUtils.convertValue(new PersonDTO("","","yyyy-MM-dd")));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Person person = new Person();
		person.setName(request.get("name").textValue());
		person.setSocialId(request.get("socialId").textValue());
		person = repository.save(person);
		response.put("message", "Person created with success");
		response.set("Person",JackJsonUtils.convertValue(person));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@GetMapping(path="/people/{socialId}",produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<JsonNode>getPersonBySocialId(@PathVariable(name="socialId") String socialId) throws IOException{
		ObjectNode response = JackJsonUtils.createNewNode();
		if(socialId == null||socialId.isEmpty()){
			response.put("message","Wrong attributes sent");
			ObjectNode node = JackJsonUtils.createNewNode();
			node.put("socialId", "");
			response.set("Person Attribute Needed", node);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
			response.put("Person parameters Needed", "name=\"\"");
			ArrayNode a = new ArrayNode(JsonNodeFactory.instance);
			a.add(response);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(a);
		}
		List<Person> lPeople = repository.findByName(name);
		return ResponseEntity.status(HttpStatus.OK).body(JackJsonUtils.listObjectToJsonNode(lPeople));
	}
	@PatchMapping(path="/people/{socialId}",produces= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<String>updatePersonBySocialId(@PathVariable(name="socialId") String socialId,@RequestBody JsonNode request) throws IOException{
		Person person = repository.findBySocialId(socialId);
		String personString = JackJsonUtils.entityToJsonString(person);
		Iterator<JsonNode> iteratorPatchOp = request.elements();
		try {
			while(iteratorPatchOp.hasNext()) {
				JsonNode jsonNodePatchOp = iteratorPatchOp.next();
				personString = patchActivity(jsonNodePatchOp, personString);
			}
			person = JackJsonUtils.jsonStringToObject(personString, Person.class);
			repository.save(person);
			return ResponseEntity.status(HttpStatus.OK).body(personString);
		}catch(TravelManagerException tme) {
			ObjectNode response = JackJsonUtils.createNewNode();
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", tme.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
		}
	}
	private String patchActivity (JsonNode patchSingleOperation, String entity) throws TravelManagerException {
		String patchOperation = patchSingleOperation.get("op").asText().toLowerCase() ;
		String path = patchSingleOperation.get("path").asText();
		JsonPatchBuilder jsonPatchBuilder = Json.createPatchBuilder();
		JsonPatch jPatch;
		switch (patchOperation) {
		case "add":
			String aaddValue = patchSingleOperation.get("value").asText();
			jPatch = jsonPatchBuilder.add(path, aaddValue).build();
			break;
		case "remove":
			jPatch = jsonPatchBuilder.remove(path).build();
			break;
		case "replace":
			String replaceValue = patchSingleOperation.get("value").asText();
			jPatch = jsonPatchBuilder.replace(path, replaceValue).build();
			break;	
		case "move":
			String from = patchSingleOperation.get("from").asText();
			jPatch = jsonPatchBuilder.move(from,path).build();
			break;				
		case "test":
			String testValue = patchSingleOperation.get("value").asText();
			jPatch = jsonPatchBuilder.test(path, testValue).build();
			break;	
		default:
			throw new TravelManagerException("The Patch Operation "+patchOperation+" isn't valid");
		}
		InputStream  personInputStream = new ByteArrayInputStream(entity.getBytes());
        JsonReader reader = Json.createReader(personInputStream);
        JsonStructure jsonStructure1 = reader.read();
        JsonStructure jsonStructure2 = jPatch.apply(jsonStructure1);
        reader.close();
        return jsonStructure2.toString();
	}
}
