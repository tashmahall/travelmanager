package com.transport.travelmanager.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transport.travelmanager.domain.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
	
	public List<Vehicle> findByName(String name);
	public Vehicle findByVehicleCode(String vehicleCode);
	
	
	

}
