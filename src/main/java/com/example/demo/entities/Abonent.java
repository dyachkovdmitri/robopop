package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Abonent {
    @Id
    @GeneratedValue
    Long id;
    String chatId;
    String userName, firstName, lastName;
    Long pupilId;
    AbonentTypeEnum abonentTypeEnum;
    String school;
    String grade;
}
