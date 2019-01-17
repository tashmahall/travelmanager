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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SEATS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= {"transport","passenger","seatTransportId"})
public class Seat {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEATS_SQ")
	@SequenceGenerator(sequenceName = "seats_seq", allocationSize = 1, name = "SEATS_SQ")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="TRANSPORT_ID", referencedColumnName="ID")
	private Transport transport;
	
	@ManyToOne
	@JoinColumn(name="PASSENGER_ID", referencedColumnName="ID")
	private Passenger passenger;
	
	@Column(name="SEAT_TRANSPORT_ID")
	private String seatTransportId;
}
