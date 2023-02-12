package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PupilScheduler {
    @Id
    @GeneratedValue
    Long id;
    Long pupilId;
    String day, time, description;
    Boolean begin;

}
