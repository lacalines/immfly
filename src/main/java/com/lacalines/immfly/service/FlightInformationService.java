package com.lacalines.immfly.service;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.lacalines.immfly.controller.FlightInformationNotFoundException;
import com.lacalines.immfly.redis.model.FlightInformation;
import com.lacalines.immfly.redis.repo.FlightInformationRepo;

@Service
public class FlightInformationService {

	@Value("${external.flight-information-tail.url}")
	private String baseUrl;

	@Autowired
	FlightInformationRepo flightInformationRepo;

	public FlightInformation[] retrieveFlightInformation(String tailNumber) {
		WebClient client = WebClient.create(baseUrl);
		FlightInformation[] flightInformationArray = client.get().uri("/v1/flight-information-tail/" + tailNumber)
				.retrieve().bodyToMono(FlightInformation[].class).block();
		return flightInformationArray;
	}

	public void cacheFlightInformation(FlightInformation[] flightInformationArray) {
		Arrays.stream(flightInformationArray).forEach(flightInformationRepo::save);
	}

	public FlightInformation findFlightInformation(String tailNumber, String flightNumber) {
		Stream<FlightInformation> stream = StreamSupport.stream(flightInformationRepo.findAll().spliterator(), false);
		return stream
				.filter(flightInformation -> flightInformation.getTailNumber().equals(tailNumber)
						&& flightInformation.getFlightNumber().equals(flightNumber))
				.findFirst().orElseThrow(() -> new FlightInformationNotFoundException());
	}

}
