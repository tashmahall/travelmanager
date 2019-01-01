package com.transport.travelmanager.utils;

import java.util.Date;

import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;

public class CreatEntityRandomDataHelper {
	public static Transport getNewTransport() {
		Vehicle vehicle = getRandomVehicle();
		Destiny destiny = getRandomDestiny();
		Transport transport = new Transport(1L,destiny,vehicle,new Date(),5);
		return transport;
	}
	public static Destiny getRandomDestiny() {
		Destiny destiny = new Destiny(1L,"New Jersey");
		return destiny;
	}
	public static Vehicle getRandomVehicle() {
		Vehicle vehicle = new Vehicle(1L,"Car",5);
		return vehicle;
	}

}
