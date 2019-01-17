package com.transport.travelmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.transport.travelmanager.domain.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
	
	public Vehicle findByName(String name);
	

}
