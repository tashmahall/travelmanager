package com.transport.travelmanager.domain.dtos;

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
	private String transportCode;
	private Long destinyId;
	private Long vehicleId;	
	private String dateTimeTravelStart;
	public TransportDTO(Long destinyId, Long vehicleId, String dateTimeTravelStart, String transportCode) {
		super();
		this.destinyId = destinyId;
		this.vehicleId = vehicleId;
		this.dateTimeTravelStart = dateTimeTravelStart;
		this.transportCode = transportCode;
	}

}

