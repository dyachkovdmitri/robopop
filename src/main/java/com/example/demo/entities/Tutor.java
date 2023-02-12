package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Tutor {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String chatId;
}
