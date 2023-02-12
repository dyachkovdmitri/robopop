package com.example.demo.repo;

import com.example.demo.entities.Abonent;
import com.example.demo.entities.AbonentTypeEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonentRepo extends CrudRepository<Abonent, Long> {
   public  Abonent findByChatIdOrUserName(String chatId, String userName);

   Abonent findFirstByAbonentTypeEnum(AbonentTypeEnum tutor);


   //String findByPupilIdAndType(Long pupilId, AbonentTypeEnum type);
}
