package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class AbonentRelation {
    @Id
    @GeneratedValue
    Long id;
    String pupilChatId;
    String secondChatId;
    AbonentTypeEnum type;
}
