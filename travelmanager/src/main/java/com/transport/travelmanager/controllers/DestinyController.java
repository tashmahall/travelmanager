package com.transport.travelmanager.controllers;

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
import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.dtos.DestinyDTO;
import com.transport.travelmanager.repositories.DestinyRepository;
import com.transport.travelmanager.utils.JackJsonUtils;

@RestController
public class DestinyController {
	@Autowired
	private DestinyRepository destinyRepository;
	
	@PostMapping(path="/adddestiny",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode> addDestiny(@RequestBody JsonNode request) throws JsonProcessingException {
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name")||request.get("name").asText().isEmpty()){
			response.put("message","Wrong attributes sent");
			response.set("Destiny Attributes",JackJsonUtils.convertValue(new DestinyDTO("")));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		Destiny destiny = new Destiny();
		destiny.setName(request.get("name").textValue());
		destiny = destinyRepository.save(destiny);
		response.put("message", "Destiny created with success");
		response.set("Destiny",JackJsonUtils.convertValue(destiny));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping(path="/getdestiny",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode>getDestinyByName(@RequestBody JsonNode request){
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name")||request.get("name").asText().isEmpty()){
			ObjectNode node = JackJsonUtils.createNewNode();
			node.put("name", "");
			response.put("message","Wrong attributes sent to consult");
			response.set("Destiny Attributes Needed",node);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		String name = request.get("name").textValue();
		Destiny destiny = destinyRepository.findByName(name);
		response.set("Destiny",JackJsonUtils.convertValue(destiny));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
