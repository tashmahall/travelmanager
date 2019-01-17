package com.transport.travelmanager.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PASSENGERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= {"person"})
public class Passenger {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PASSENGERS_SQ")
	@SequenceGenerator(sequenceName = "passengers_seq", allocationSize = 1, name = "PASSENGERS_SQ")
	private Long id;
	@ManyToOne
	@JoinColumn(name="PERSON_ID", referencedColumnName="ID")
	private Person person;
	@OneToMany(mappedBy="passenger")
	private List<Seat> seats;
	
	public Passenger(long id, Person person) {
		this.id= id;
		this.person=person;
	}

}
