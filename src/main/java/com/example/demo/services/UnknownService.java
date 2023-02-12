package com.example.demo.services;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownService implements  CreateAnswer{
    @Override
    public String createAnswer(Update update) {
        return "Ура!" + update.getMessage().getFrom().getFirstName() + " "+ update.getMessage().getFrom().getLastName() +": "+ update.getMessage().getText();
    }
}