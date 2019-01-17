package com.transport.travelmanager.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TRANSPORTS")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of= {"id","destiny","vehicle"})
public class Transport {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSP_SEQ")
	@SequenceGenerator(sequenceName = "transport_seq", allocationSize = 1, name = "TRANSP_SEQ")
	private Long id;
	@ManyToOne()
	@JoinColumn(name="DESTINY_ID", referencedColumnName="ID")
	private Destiny destiny;
	@ManyToOne
	@JoinColumn(name="VEHICLE_ID", referencedColumnName="ID")
	private Vehicle vehicle;	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_TIME_TRAVEL_START")
	private Date dateTimeTravelStart;
	@JsonProperty(access=Access.WRITE_ONLY)
	@OneToMany(mappedBy="transport",fetch=FetchType.LAZY)
	private List<Seat> listSeats;
	@Column(name="CODE")
	private String transportCode;
	public Transport(Long id, Destiny destiny, Vehicle vehicle, Date dateTimeTravelStart, String transportCode) {
		super();
		this.id = id;
		this.destiny = destiny;
		this.vehicle = vehicle;
		this.dateTimeTravelStart = dateTimeTravelStart;
		this.transportCode = transportCode;
	}
	
}
