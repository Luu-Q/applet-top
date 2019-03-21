package com.applet.mongodb.dao;

import com.applet.mongodb.MongoTestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoTestRepository extends MongoRepository<MongoTestEntity, String> {

    MongoTestEntity findByFirstName(String firstName);

    List<MongoTestEntity> findByLastName(String lastName);

}