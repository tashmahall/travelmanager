package com.transport.travelmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.repositories.VehicleRepository;

@Service
public class VehicleService {
	@Autowired
	private VehicleRepository repository;

	public Vehicle save(Vehicle vehicle) throws TravelManagerException {
		vehicle.setName(vehicle.getName().toUpperCase());
		vehicle.setVehicleCode(vehicle.getVehicleCode().toUpperCase());
		Vehicle vTemp = repository.findByVehicleCode(vehicle.getVehicleCode());
		if(vTemp != null) {
			throw new TravelManagerException("The vehicle code alredy exist in the Database.");
		}
		return repository.save(vehicle);
		
	}
	public List<Vehicle> findByName(String name) {
		return repository.findByName(name);
	}
	public Vehicle findByVehicleCode(String vehicleCode) {
		return repository.findByVehicleCode(vehicleCode);
	}
}
