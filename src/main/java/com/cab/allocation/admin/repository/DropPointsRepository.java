package com.cab.allocation.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cab.allocation.admin.model.DropPoints;

public interface DropPointsRepository extends MongoRepository<DropPoints, String> {

}
