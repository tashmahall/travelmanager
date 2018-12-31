package com.transport.travelmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PASSENGERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEHIC_SEQ")
	@SequenceGenerator(sequenceName = "vehicle_seq", allocationSize = 1, name = "VEHIC_SEQ")
	private Long id;
	@Column(name="NAME", unique=true)
	private String name;
	@Column (name="CAPACITY", nullable=false)
	private Integer capacity;

}
