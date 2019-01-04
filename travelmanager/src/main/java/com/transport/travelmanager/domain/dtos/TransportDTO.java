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
	public TransportDTO(Long destinyId, Long vehicleId, String dateTimeTravelStart) {
		super();
		this.destinyId = destinyId;
		this.vehicleId = vehicleId;
		this.dateTimeTravelStart = dateTimeTravelStart;
	}
	private Long destinyId;
	private Long vehicleId;	
	private String dateTimeTravelStart;
}

