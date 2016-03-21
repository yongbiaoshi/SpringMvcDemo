package com.tsingda.smd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsingda.smd.dao.repository.HumanRepository;
import com.tsingda.smd.model.mongo.Human;

@RequestMapping(value = "/mongo")
@Controller
public class MongoController {
    @Autowired
    private HumanRepository humanRepository;

    @RequestMapping(value = "new")
    public @ResponseBody Human newHuman() {
        Human human = new Human();
        human.setName("王立博");
        human.setAge(38);
        human.setAddress("八大胡同");
        human = humanRepository.insert(human);
        return human;
    }

    @RequestMapping(value = "show")
    public @ResponseBody Page<Human> showHuman(Pageable pageable) {
        Page<Human> l = humanRepository.findAll(pageable);
        return l;
    }

    @RequestMapping(value = "list")
    public @ResponseBody Page<Human> showHuman(String name, Pageable pageable) {
        Page<Human> page = humanRepository.someCustomMethod(name, pageable);
        return page;
    }
}
