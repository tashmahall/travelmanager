package com.transport.travelmanager.repository;

import java.sql.Types;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.repository.rowmappers.TransportRowMapper;

@Repository
public class TransportRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Transport createNewTransport(Long destinyId,Long vehicleId,Integer capacity ,Date dateTimeTraveStart) {
		Long nextId = jdbcTemplate.queryForObject("select transport.nextval from dual",Long.class);
		String sql = "INSERT INTO TRANSPORT (ID, DATE_TIME_TRAVEL_START, SEATS, DESTINY_ID, VEHICLE_ID) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[] {nextId,dateTimeTraveStart,capacity,destinyId,vehicleId},new int[] {Types.NUMERIC,Types.DATE,Types.INTEGER,Types.NUMERIC,Types.NUMERIC});
		return findById(nextId);
	}
	public Transport findById(Long transportId) {
		String sql = "SELECT t.id as T_ID, t.date_time_travel_start as T_DATE_TIME_TRAVEL_START, t.seats as T_SEATS,"
				+ " t.destiny_id as T_DESTINY_ID, t.vehicle_id as T_VEHICLE_ID, d.name as D_NAME, v.capacity as V_CAPACITY, v.name as V_NAME "
				+ "FROM transports t right JOIN destinations d on t.destiny_id=d.id right join vehicles v on t.vehicle_id=v.id where t.id=?";
		Transport transport = jdbcTemplate.queryForObject(sql, new TransportRowMapper());
		return transport;
	}
}
