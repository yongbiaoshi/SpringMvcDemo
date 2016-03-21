package com.tsingda.smd.dao.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tsingda.smd.model.mongo.Human;

public interface HumanRepositoryCustom {

    Page<Human> someCustomMethod(String name, Pageable pageable);
}
