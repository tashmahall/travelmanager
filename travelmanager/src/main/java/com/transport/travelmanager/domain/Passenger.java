package com.transport.travelmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Passenger {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASS_SEQ")
	@SequenceGenerator(sequenceName = "passengers_seq", allocationSize = 1, name = "PASS_SEQ")
	private Long id;
	@Column(name="NAME")
	private String name;
	@ManyToOne
	@JoinColumn(name="TRANSPORT_ID", referencedColumnName="ID")
	private Transport transport;
	public Passenger(long id, String name) {
		this.id= id;
		this.name=name;
	}

}
