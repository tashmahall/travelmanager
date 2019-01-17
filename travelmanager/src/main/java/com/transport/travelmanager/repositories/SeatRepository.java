package com.transport.travelmanager.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transport.travelmanager.domain.Passenger;
import com.transport.travelmanager.domain.Seat;
import com.transport.travelmanager.domain.Transport;

public interface SeatRepository extends CrudRepository<Seat, Long>  {
	public List<Seat> findByPassenger(Passenger passenger);
	public List<Seat> findByTransport(Transport transport);

}
