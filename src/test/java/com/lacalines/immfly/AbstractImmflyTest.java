package com.lacalines.immfly;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacalines.immfly.redis.repo.FlightInformationRepo;

import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractImmflyTest {

	@Container
	public static GenericContainer redis = new GenericContainer("redis:5.0.3-alpine").withExposedPorts(6379);

	@Autowired
	public FlightInformationRepo flightInformationRepo;

	public static MockWebServer mockWebServer;

	public ObjectMapper mapper = new ObjectMapper();

	@BeforeAll
	static void start() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
	}

	@BeforeEach
	void setup() {
		flightInformationRepo.deleteAll();
	}

	@AfterAll
	static void shutdown() throws IOException {
		mockWebServer.shutdown();
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry r) throws IOException {
		r.add("spring.redis.host", redis::getContainerIpAddress);
		r.add("spring.redis.port", redis::getFirstMappedPort);
		r.add("external.flight-information-tail.url", () -> mockWebServer.url("/").toString());
	}

}
