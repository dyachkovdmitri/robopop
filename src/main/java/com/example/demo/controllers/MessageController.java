package com.example.demo.controllers;

import com.example.demo.MessageRepo;
import com.example.demo.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MessageController {
    @Autowired
    MessageRepo repo;
    @GetMapping("message/all")
    Iterable<Message> get(){
        return repo.findAll();
    }
}
