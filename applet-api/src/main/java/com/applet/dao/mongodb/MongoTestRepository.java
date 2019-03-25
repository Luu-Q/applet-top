package com.applet.dao.mongodb;


import com.applet.entity.mongodb.MongoTestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoTestRepository extends MongoRepository<MongoTestEntity, String> {

    MongoTestEntity findByFirstName(String firstName);

    List<MongoTestEntity> findByLastName(String lastName);

}