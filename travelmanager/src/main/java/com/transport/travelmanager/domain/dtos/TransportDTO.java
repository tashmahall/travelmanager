package com.transport.travelmanager.domain.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransportDTO {
	private Long id;
	private Long destinyId;
	private Long vehicleId;	
	private Date dateTimeTravelStart;

}
