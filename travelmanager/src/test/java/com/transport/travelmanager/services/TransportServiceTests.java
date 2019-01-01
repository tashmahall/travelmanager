package com.transport.travelmanager.services;

import static com.transport.travelmanager.utils.CreatEntityRandomDataHelper.getNewTransport;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;
import com.transport.travelmanager.exceptions.TransportException;
import com.transport.travelmanager.repository.DestinyRepository;
import com.transport.travelmanager.repository.TransportRepository;
import com.transport.travelmanager.repository.VehicleRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceTests {
	@InjectMocks
	private TransportService transportService;
	@Mock(stubOnly=false)
	private TransportRepository transportRepository;
	@Mock(stubOnly=false)
	private VehicleRepository vehicleRepository;
	@Mock(stubOnly=false)
	private DestinyRepository destinyRepository;
	@Mock
	private JdbcTemplate JdbcTemplate;
	
	@Test
	public void createNewTransport() throws TransportException {
		Transport transport = getNewTransport();
		Optional<Destiny> oDestiny = Optional.of(transport.getDestiny());
		Optional<Vehicle> oVehicle = Optional.of(transport.getVehicle());
		
		when(destinyRepository.findById(anyLong())).thenReturn(oDestiny);
		when(vehicleRepository.findById(anyLong())).thenReturn(oVehicle);
		when(transportRepository.createNewTransport(transport.getDestiny().getId(), transport.getVehicle().getId(), transport.getVehicle().getCapacity(), transport.getDateTimeTravelStart())).thenReturn(transport);
		
		Transport tTemp = transportService.createNewTransport(transport.getDestiny(), transport.getVehicle(), transport.getDateTimeTravelStart());
		
		assertEquals("The Transport received was different than the expected",transport,tTemp);
	}


}
