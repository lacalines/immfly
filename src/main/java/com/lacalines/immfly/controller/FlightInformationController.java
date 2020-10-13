package com.lacalines.immfly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lacalines.immfly.redis.model.FlightInformation;
import com.lacalines.immfly.service.FlightInformationService;

@RestController
public class FlightInformationController {

	@Autowired
	FlightInformationService flightInformationService;

	@Secured("ROLE_ADMIN")
	@GetMapping("/v1/flight-information/{tailNumber}/{flightNumber}")
	FlightInformation flightInformation(@PathVariable String tailNumber, @PathVariable String flightNumber) {
		FlightInformation[] flightInformationArray = flightInformationService.retrieveFlightInformation(tailNumber);
		flightInformationService.cacheFlightInformation(flightInformationArray);
		return flightInformationService.findFlightInformation(tailNumber, flightNumber);
	}

}
