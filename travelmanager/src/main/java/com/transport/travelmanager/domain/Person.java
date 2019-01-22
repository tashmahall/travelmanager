package com.transport.travelmanager.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_SQ")
	@SequenceGenerator(sequenceName = "person_seq", allocationSize = 1, name = "PERSON_SQ")
	private Long id;
	@Column(name="NAME", nullable=false)
	private String name;
	@Column(name="SOCIAL_ID",nullable=false, unique=true)
	private String socialId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", locale="pt-BR", timezone = "Brazil/East")
	@Temporal(TemporalType.DATE)
	@Column(name="BIRTH_DATE")
	private Date birthDate;
	public Person(String name, String socialId) {
		super();
		this.name = name;
		this.socialId = socialId;
	}
	public Person(Long id, String name, String socialId) {
		super();
		this.id = id;
		this.name = name;
		this.socialId = socialId;
	}
}
