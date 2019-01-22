package com.transport.travelmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transport.travelmanager.domain.Person;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.repositories.PersonRepository;

@Service
public class PersonService {
	@Autowired
	private PersonRepository repository;
	
	public Person save (Person person) throws TravelManagerException {
		if(repository.findBySocialId(person.getSocialId())!=null) {
			throw new TravelManagerException("The social ID alredy exist");
		}
		person.setName(person.getName().toUpperCase());
		person.setSocialId(person.getSocialId().toUpperCase());
		return repository.save(person);
	}
	public Person findBySocialId(String socialId) {
		return repository.findBySocialId(socialId.toUpperCase());
	}
	public List<Person> findByName(String name) {
		return repository.findByName(name.toUpperCase());
	}
}
