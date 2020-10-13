package com.lacalines.immfly.redis.model;

public class Airport {

	private String code;
	private String city;
	private String alternate_ident;
	private String airport_name;

	public Airport() {
	}

	public Airport(String code, String city, String alternate_ident, String airport_name) {
		this.code = code;
		this.city = city;
		this.alternate_ident = alternate_ident;
		this.airport_name = airport_name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAlternate_ident() {
		return alternate_ident;
	}

	public void setAlternate_ident(String alternate_ident) {
		this.alternate_ident = alternate_ident;
	}

	public String getAirport_name() {
		return airport_name;
	}

	public void setAirport_name(String airport_name) {
		this.airport_name = airport_name;
	}

}
