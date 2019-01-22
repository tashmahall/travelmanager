package com.transport.travelmanager.repositories.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.transport.travelmanager.domain.Destiny;
import com.transport.travelmanager.domain.Transport;
import com.transport.travelmanager.domain.Vehicle;

public class TransportRowMapper implements RowMapper<Transport> {
	@Override
	public Transport mapRow(ResultSet rs, int rowNum) throws SQLException {
		Destiny destiny = new Destiny(rs.getLong("T_DESTINY_ID"),rs.getString("D_NAME"));
		Vehicle vehicle = new Vehicle(rs.getLong("T_VEHICLE_ID"),rs.getString("V_CODE"),rs.getString("V_NAME"),rs.getInt("V_CAPACITY"));
		Transport transport = new Transport(rs.getLong("T_ID"), destiny,  vehicle, rs.getDate("T_DATE_TIME_TRAVEL_START"), rs.getString("T_CODE"));
		return transport;
	}

}
