package com.transport.travelmanager.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
	private String name;
	private Long id;
	private String socialId;
	private String birthDate;
	public PersonDTO(String name,String socialId, String birthDate) {
		super();
		this.name = name;
		this.socialId = socialId;
		this.birthDate = birthDate;
	}
}

