package com.transport.travelmanager.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinyDTO {
	private String name;
	private Long id;
	public DestinyDTO(String name) {
		super();
		this.name = name;
	}
}

