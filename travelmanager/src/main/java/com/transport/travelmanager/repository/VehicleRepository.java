package com.transport.travelmanager.repository;

import org.springframework.data.repository.CrudRepository;

import com.transport.travelmanager.domain.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

}
