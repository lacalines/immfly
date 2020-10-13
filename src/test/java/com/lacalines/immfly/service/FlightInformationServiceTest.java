package com.lacalines.immfly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lacalines.immfly.AbstractImmflyTest;
import com.lacalines.immfly.controller.FlightInformationNotFoundException;
import com.lacalines.immfly.redis.model.Airport;
import com.lacalines.immfly.redis.model.FlightInformation;

import okhttp3.mockwebserver.MockResponse;

public class FlightInformationServiceTest extends AbstractImmflyTest {

	@Autowired
	FlightInformationService flightInformationService;

	private FlightInformation[] createFlights(int numberOfFlights) {
		return IntStream.range(0, numberOfFlights).mapToObj(String::valueOf).map((s) -> {
			return new FlightInformation(s, s, s, s, s, s, s, s, false, false, false,
					new Airport("Origin", "Origin", "Origin", "Origin"),
					new Airport("Destination", "Destination", "Destination", "Destination"));
		}).toArray(FlightInformation[]::new);
	}

	private void cacheFlights(int numberOfFlights) {
		flightInformationService.cacheFlightInformation(createFlights(numberOfFlights));
	}

	@Test
	public void retrieveFlightInformationTest() throws JsonProcessingException {
		mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(createFlights(15)))
				.addHeader("Content-Type", "application/json"));
		assertEquals(15, flightInformationService.retrieveFlightInformation("ignoredTail").length);
	}

	@Test
	public void retrieveEmptyFlightInformationTest() throws JsonProcessingException {
		mockWebServer.enqueue(new MockResponse().setBody("[]").addHeader("Content-Type", "application/json"));
		assertEquals(0, flightInformationService.retrieveFlightInformation("ignoredTail").length);
	}

	@Test
	public void cacheFlightInformationTest() {
		flightInformationService.cacheFlightInformation(createFlights(15));
		assertEquals(flightInformationRepo.count(), 15);
	}

	@Test
	public void cacheEmptyFlightInformationTest() {
		flightInformationService.cacheFlightInformation(new FlightInformation[0]);
		assertEquals(flightInformationRepo.count(), 0);
	}

	@Test
	public void findFlightInformationTest() {
		cacheFlights(10);
		assertEquals(flightInformationService.findFlightInformation("5", "5").getIdent(), "5");
	}

	@Test
	public void findFlightInformationNotFoundTest() {
		cacheFlights(10);
		assertThrows(FlightInformationNotFoundException.class,
				() -> flightInformationService.findFlightInformation("MissingTailNumber", "MissingFlightNumber"));
	}

	@Test
	public void findFlightInformationInvalidTailNumberTest() {
		cacheFlights(1);
		assertThrows(FlightInformationNotFoundException.class,
				() -> flightInformationService.findFlightInformation("InvalidTailNumber", "0"));
		assertEquals(flightInformationService.findFlightInformation("0", "0").getIdent(), "0");
	}

	@Test
	public void findFlightInformationInvalidFlightNumberTest() {
		cacheFlights(1);
		assertThrows(FlightInformationNotFoundException.class,
				() -> flightInformationService.findFlightInformation("0", "InvalidFlightNumber"));
		assertEquals(flightInformationService.findFlightInformation("0", "0").getIdent(), "0");
	}

}
