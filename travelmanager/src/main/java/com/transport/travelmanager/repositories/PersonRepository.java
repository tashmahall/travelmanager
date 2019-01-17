package com.transport.travelmanager.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transport.travelmanager.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
		public List<Person> findByName(String name);
		public Person findBySocialId(String socialId);
}
