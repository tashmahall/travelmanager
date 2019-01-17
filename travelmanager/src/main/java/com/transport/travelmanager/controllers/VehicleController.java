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
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.domain.dtos.VehicleDTO;
import com.transport.travelmanager.repositories.VehicleRepository;
import com.transport.travelmanager.utils.JackJsonUtils;

@RestController
public class VehicleController {
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@PostMapping(path="/addvehicle",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode> addDestiny(@RequestBody JsonNode request) throws JsonProcessingException {
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name") || !request.has("capacity") || request.get("name").textValue().isEmpty() || request.get("capacity").asInt()<1){
			response.put("message","Wrong attributes sent");
			response.set("Vehicle",JackJsonUtils.convertValue(new VehicleDTO("",0)));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		Vehicle vehicle = new Vehicle();
		vehicle.setCapacity(request.get("capacity").asInt());
		vehicle.setName(request.get("name").textValue());
		vehicle = vehicleRepository.save(vehicle);
		response.set("Vehicle", JackJsonUtils.convertValue(vehicle));
		response.put("message", "Vehicle created with success");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PostMapping(path="/getvehicle",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode>getDestinyByName(@RequestBody JsonNode request){
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("name") || request.get("name").textValue().isEmpty()){
			response.put("message","Wrong attributes sent");
			response.put("name", "");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
		}
		String name = request.get("name").textValue();
		Vehicle vehicle = vehicleRepository.findByName(name);
		response.set("Vehicle", JackJsonUtils.convertValue(vehicle));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
