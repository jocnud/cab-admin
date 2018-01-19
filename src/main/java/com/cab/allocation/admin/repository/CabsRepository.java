package com.cab.allocation.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cab.allocation.admin.model.Cab;

public interface CabsRepository extends MongoRepository<Cab, String> {

	public Cab findByCabId(String cabId);
}
