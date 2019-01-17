package com.transport.travelmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="VEHICLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= {"name"})
public class Vehicle {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VECHICLE_SQ")
	@SequenceGenerator(sequenceName = "vehicle_seq", allocationSize = 1, name = "VECHICLE_SQ")
	private Long id;
	@Column(name="NAME", unique=true)
	private String name;
	@Column (name="CAPACITY", nullable=false)
	private Integer capacity;

}
