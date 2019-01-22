package com.transport.travelmanager.controllers.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.services.VehicleService;
import com.transport.travelmanager.utils.JackJsonUtils;

@RestController
public class VehicleControllerRestful {
	@Autowired
	private VehicleService service;
	
	@PostMapping(path="/vehicle",consumes= {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode> addVehicle(@RequestBody JsonNode request) throws JsonProcessingException {
		ObjectNode response = JackJsonUtils.createNewNode();
		if(!request.has("vehicleCode")||request.get("vehicleCode").asText().isEmpty()||!request.has("name") || request.get("name").textValue().isEmpty() || !request.has("capacity") || request.get("capacity").asInt()<1){
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", "Wrong attributes sent");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		Vehicle vehicle = new Vehicle();
		vehicle.setCapacity(request.get("capacity").asInt());
		vehicle.setName(request.get("name").textValue());
		vehicle.setVehicleCode(request.get("vehicleCode").asText());
		try {
			vehicle = service.save(vehicle);
			response = JackJsonUtils.convertValue(vehicle);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (TravelManagerException e) {
			response.put("code","400");
			response.put("error", "Bad Request");
			response.put("cause", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	@GetMapping(path="/vehicle/{vehicleCode}",produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<JsonNode>findVehicleByVehicleCode(@PathVariable(name="vehicleCode") String vehicleCode){
		ObjectNode response = JackJsonUtils.createNewNode();
		Vehicle vehicle = service.findByVehicleCode(vehicleCode);
		response =  JackJsonUtils.convertValue(vehicle);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
