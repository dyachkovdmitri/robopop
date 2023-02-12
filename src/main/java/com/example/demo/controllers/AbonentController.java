package com.example.demo.controllers;

import com.example.demo.entities.Abonent;
import com.example.demo.entities.AbonentTypeEnum;
import com.example.demo.repo.AbonentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AbonentController {
    @Autowired
   AbonentRepo repo;

    @GetMapping("abonent/all")
    Iterable<Abonent> get(){
        return repo.findAll();
    }

    @PostMapping("abonent/create")
    Abonent post(@RequestBody Abonent abonent){
        return repo.save(abonent);
    }

    @GetMapping("abonent/delete/{id}")
    String post(@PathVariable Long id){
         repo.deleteById(id);
         return "deleted";
    }

}
