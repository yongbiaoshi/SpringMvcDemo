package com.tsingda.smd.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tsingda.smd.dao.repository.custom.HumanRepositoryCustom;
import com.tsingda.smd.model.mongo.Human;

public interface HumanRepository extends MongoRepository<Human, String>, HumanRepositoryCustom {

}