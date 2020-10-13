package com.lacalines.immfly.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FlightInformationNotFoundAdvice {

	@ExceptionHandler(FlightInformationNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void flightInformationNotFoundHandler(FlightInformationNotFoundException e) {
		
	}

}
