package com.transport.travelmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.transport.travelmanager.domain.Destiny;

public interface DestinyRepository extends CrudRepository<Destiny, Long> {
		public Destiny findByName(String name);

}
