package com.transport.travelmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.transport.travelmanager.domain.Transport;

public interface ITransportRepository extends CrudRepository<Transport, Long> {

}
