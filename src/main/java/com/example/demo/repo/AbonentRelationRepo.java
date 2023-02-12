package com.example.demo.repo;

import com.example.demo.entities.Abonent;
import com.example.demo.entities.AbonentRelation;
import com.example.demo.entities.AbonentTypeEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonentRelationRepo extends CrudRepository<AbonentRelation, Long> {

    public AbonentRelation findFirstByPupilChatIdAndType(String pupilChatId, AbonentTypeEnum type);
}
