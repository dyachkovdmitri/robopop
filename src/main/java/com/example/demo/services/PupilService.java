package com.example.demo.services;

import com.example.demo.entities.Abonent;
import com.example.demo.entities.AbonentRelation;
import com.example.demo.entities.AbonentTypeEnum;
import com.example.demo.repo.AbonentRelationRepo;
import com.example.demo.repo.AbonentRepo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class PupilService implements CreateAnswer {
    @Autowired
    AbonentRelationRepo abonentRelationRepo;
    @Autowired
    AbonentRepo abonentRepo;

    @Override
    public String createAnswer(Update update) {
        return "нереализовано";
    }

    public String findTutor(Update update) {
        AbonentRelation byPupilChatIdAndType = abonentRelationRepo.findFirstByPupilChatIdAndType(update.getMessage().getChatId().toString(), AbonentTypeEnum.TUTOR);
        if (byPupilChatIdAndType != null) {
            return byPupilChatIdAndType.getSecondChatId();
        } else {
           Abonent newTutor = abonentRepo.findFirstByAbonentTypeEnum(AbonentTypeEnum.TUTOR);
           AbonentRelation rel = new AbonentRelation();
           rel.setPupilChatId(update.getMessage().getChatId().toString());
           rel.setSecondChatId(newTutor.getChatId().toString());
           rel.setType(AbonentTypeEnum.TUTOR);
           abonentRelationRepo.save(rel);
           return newTutor.getChatId().toString();
        }
    }

}
