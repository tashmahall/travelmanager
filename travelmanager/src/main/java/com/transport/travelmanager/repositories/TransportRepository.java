package com.transport.travelmanager.repositories;

import java.sql.Types;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.transport.travelmanager.domain.Seat;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.repositories.rowmappers.TransportRowMapper;

@Repository
public class TransportRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SeatRepository seatRepository;
	
	public Transport createNewTransport(Long destinyId,Long vehicleId,Integer capacity ,Date dateTimeTraveStart, String transportCode) {
		Long nextId = jdbcTemplate.queryForObject("select transport_seq.nextval from dual",Long.class);
		String sql = "INSERT INTO TRANSPORTS (ID, DATE_TIME_TRAVEL_START, DESTINY_ID, VEHICLE_ID, CODE) VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[] {nextId,dateTimeTraveStart,capacity,destinyId,vehicleId},new int[] {Types.NUMERIC,Types.DATE,Types.NUMERIC,Types.NUMERIC,Types.VARCHAR});
		Transport transport = findById(nextId);
		LinkedList<Seat> listSeat= new LinkedList<Seat>();
		for(int i=0;i<capacity;i++) {
			Seat seat = new Seat();
			seat.setTransport(transport);
			listSeat.add(seat);
		}
		seatRepository.saveAll(listSeat);
		return transport;
	}
	public Transport findById(Long transportId) {
		String sql = "SELECT t.id as T_ID, t.date_time_travel_start as T_DATE_TIME_TRAVEL_START, t.code as T_CODE, t.destiny_id as T_DESTINY_ID, \n" + 
				"	t.vehicle_id as T_VEHICLE_ID, d.name as D_NAME, v.capacity as V_CAPACITY, v.name as V_NAME \n" + 
				"	FROM transports t right JOIN destinations d on t.destiny_id=d.id right join vehicles v on t.vehicle_id=v.id where t.id=?";
		Transport transport = jdbcTemplate.queryForObject(sql,new Object[] {transportId} ,new TransportRowMapper());
		return transport;
	}
}
