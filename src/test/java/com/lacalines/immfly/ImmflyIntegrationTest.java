package com.lacalines.immfly;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.lacalines.immfly.redis.model.Airport;
import com.lacalines.immfly.redis.model.FlightInformation;

import okhttp3.mockwebserver.MockResponse;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class ImmflyIntegrationTest extends AbstractImmflyTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(roles = {"ADMIN"})
	void testFlight() throws Exception {
		FlightInformation[] flightInformation = { new FlightInformation("IBB653", "IBB653-1581399936-airline-0136",
				"IBB", "NT", "653", "EC-MYT", "Form_Airline", "IBE123", false, false, false,
				new Airport("GCXO", "Tenerife", "TFN", "Tenerife North (Los Rodeos)"),
				new Airport("GCGM", "La Gomera", "GMZ", "La Gomera")) };
		mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(flightInformation))
				.addHeader("Content-Type", "application/json"));
		mockMvc.perform(get("/v1/flight-information/EC-MYT/653")).andExpect(status().isOk())
				.andExpect(jsonPath("$.ident", is("IBB653")));
	}

	@Test
	@WithMockUser(roles = {"ADMIN"})
	void testFlightNotFound() throws Exception {
		mockWebServer.enqueue(new MockResponse().setBody("[]").addHeader("Content-Type", "application/json"));
		mockMvc.perform(get("/v1/flight-information/EC-MYT/653")).andExpect(status().is4xxClientError());
	}

	@Test
	void testUnauthorizedAdminUser() throws Exception {
		mockMvc.perform(get("/v1/flight-information/EC-MYT/653")).andExpect(status().is4xxClientError());
	}
}
