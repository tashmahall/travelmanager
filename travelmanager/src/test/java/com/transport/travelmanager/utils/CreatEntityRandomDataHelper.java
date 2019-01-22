package com.transport.travelmanager.utils;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Person;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;

public class CreatEntityRandomDataHelper {
	public static Transport getNewTransport() {
		Vehicle vehicle = getRandomVehicle();
		Destiny destiny = getRandomDestiny();
		Transport transport = new Transport(1L,destiny,vehicle,new Date(), RandomStringUtils.random(4));
		return transport;
	}
	public static Destiny getRandomDestiny() {
		Destiny destiny = new Destiny((new Random()).nextLong(),RandomStringUtils.random(20)+" City");
		return destiny;
	}
	public static Vehicle getRandomVehicle() {
		Vehicle vehicle = new Vehicle((new Random()).nextLong(),RandomStringUtils.randomAlphanumeric(5),RandomStringUtils.randomAlphanumeric(20),(new Random()).nextInt(10000));
		return vehicle;
	}
	public static Person getRandomPerson() {
		Person person = new Person((new Random()).nextLong(), RandomStringUtils.random(20), new String (GeraCpfCnpj.cpf()));
		return person;
	}

}
