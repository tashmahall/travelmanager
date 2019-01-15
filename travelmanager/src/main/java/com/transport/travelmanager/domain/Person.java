package com.transport.travelmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PEOPLE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of= {"socialId"})
public class Person {
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_SQ")
	@SequenceGenerator(sequenceName = "person_seq", allocationSize = 1, name = "PERSON_SQ")
	private Long id;
	@Column(name="NAME", nullable=false)
	private String name;
	@Column(name="SOCIAL_ID",nullable=false, unique=true)
	private String socialId;
	public Person(String name, String socialId) {
		super();
		this.name = name;
		this.socialId = socialId;
	}

}
