package com.tsingda.smd.dao.repository.custom.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.tsingda.smd.dao.repository.custom.HumanRepositoryCustom;
import com.tsingda.smd.model.mongo.Human;

public class HumanRepositoryImpl implements HumanRepositoryCustom {

    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public Page<Human> someCustomMethod(String name, Pageable pageable) {
        Query query = query(where("name").regex(name + "*"));
        long total = mongoTemplate.count(query, Human.class);
        List<Human> l = mongoTemplate.find(query.with(pageable), Human.class);
        return new PageImpl<Human>(l, pageable, total);
    }
}
