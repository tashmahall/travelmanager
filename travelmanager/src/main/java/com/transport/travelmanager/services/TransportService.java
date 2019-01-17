package com.transport.travelmanager.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TravelManagerException;
import com.transport.travelmanager.repositories.DestinyRepository;
import com.transport.travelmanager.repositories.TransportRepository;
import com.transport.travelmanager.repositories.VehicleRepository;

@Service
public class TransportService {

	@Autowired
	private TransportRepository transportRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private DestinyRepository destinyRepository;
	
	public Transport createNewTransport(Destiny destinyId,Vehicle vehicleId, Date dateTimeTravelStart, String transportCode) throws TravelManagerException {
		if( destinyId == null ||  vehicleId == null || dateTimeTravelStart==null) {
			throw new TravelManagerException("The destiny, vehicle and date time to travel Start must be informed");
		}
		if( destinyId.getId()==null||vehicleId.getId()==null) {
			throw new TravelManagerException("The destiny or vehicle have not been indentified by the system, yet");
		}
		Optional<Destiny> oDestiny = destinyRepository.findById(destinyId.getId());
		Optional<Vehicle> oVehicle = vehicleRepository.findById(vehicleId.getId());
		if( oDestiny==null||oVehicle==null||!oDestiny.isPresent()||!oVehicle.isPresent()) {
			throw new TravelManagerException("The destiny or vehicle have not been indentified by the system, yet");
		}
		Vehicle vTemp = oVehicle.get();
		return transportRepository.createNewTransport(destinyId.getId(), vehicleId.getId(), vTemp.getCapacity(), dateTimeTravelStart,transportCode);
	}
}
