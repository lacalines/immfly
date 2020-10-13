package com.lacalines.immfly.redis.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("FlightInformation")
public class FlightInformation implements Serializable {

	private static final long serialVersionUID = 2658640764023809749L;

	@Id private String ident;
	private String faFlightID;
	private String airline;
	private String airline_iata;
	private String flightNumber;
	private String tailNumber;
	private String type;
	private String codeshares;
	private boolean blocked;
	private boolean diverted;
	private boolean cancelled;
	private Airport origin;
	private Airport destination;

	public FlightInformation() {
	}

	public FlightInformation(String ident, String faFlightID, String airline, String airline_iata, String flightNumber,
			String tailNumber, String type, String codeshares, boolean blocked, boolean diverted, boolean cancelled,
			Airport origin, Airport destination) {
		this.ident = ident;
		this.faFlightID = faFlightID;
		this.airline = airline;
		this.airline_iata = airline_iata;
		this.flightNumber = flightNumber;
		this.tailNumber = tailNumber;
		this.type = type;
		this.codeshares = codeshares;
		this.blocked = blocked;
		this.diverted = diverted;
		this.cancelled = cancelled;
		this.origin = origin;
		this.destination = destination;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getFaFlightID() {
		return faFlightID;
	}

	public void setFaFlightID(String faFlightID) {
		this.faFlightID = faFlightID;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getAirline_iata() {
		return airline_iata;
	}

	public void setAirline_iata(String airline_iata) {
		this.airline_iata = airline_iata;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getTailNumber() {
		return tailNumber;
	}

	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeshares() {
		return codeshares;
	}

	public void setCodeshares(String codeshares) {
		this.codeshares = codeshares;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isDiverted() {
		return diverted;
	}

	public void setDiverted(boolean diverted) {
		this.diverted = diverted;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Airport getOrigin() {
		return origin;
	}

	public void setOrigin(Airport origin) {
		this.origin = origin;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

}
