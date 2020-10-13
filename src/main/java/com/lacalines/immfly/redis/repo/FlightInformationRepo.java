package com.lacalines.immfly.redis.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacalines.immfly.redis.model.FlightInformation;

@Repository
public interface FlightInformationRepo extends CrudRepository<FlightInformation, String> {

}
