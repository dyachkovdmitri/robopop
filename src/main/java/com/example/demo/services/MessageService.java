package com.example.demo.services;

import com.example.demo.MessageRepo;
import com.example.demo.entities.Abonent;
import com.example.demo.entities.AbonentTypeEnum;
import com.example.demo.entities.Message;
import com.example.demo.repo.AbonentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageService {
    @Autowired
    MessageRepo repo;

    @Autowired
    AbonentRepo abonentRepo;

    @Autowired
    PupilService pupilService;

    @Autowired
    UnknownService unknownService;
    @Autowired
    MessageRepo messageRepo;


    public Message save(SendMessage sendMessage, String consumerChatId) {
        Message message = new Message();
        message.setReceiverChatId(sendMessage.getChatId());
        message.setConsumerChatId(consumerChatId);
        message.setMessage(sendMessage.getText());
        repo.save(message);
        return message;
    }

    public List<SendMessage> prepareMessage(Update update, String fileUrl) {
        return findRecepient(update);
    }

    private List<SendMessage> findRecepient(Update update) {
        System.out.println(update);
        Abonent sender = abonentRepo.findByChatIdOrUserName(update.getMessage().getChatId().toString(), update.getMessage().getFrom().getUserName());
        sender = saveSenderIfNeed(sender, update);
        SendMessage message = new SendMessage();
        String text = update.getMessage().getText();
        if(text==null){
            text="";
        }
        message.setText(update.getMessage().getText());
        String chatId = update.getMessage().getChatId().toString();
        if (text.startsWith("U")) {
            sender.setAbonentTypeEnum(AbonentTypeEnum.TUTOR);
        }
        switch (sender.getAbonentTypeEnum()) {
            case UNKNOWN -> {
                chatId = "203523943";
                text = unknownService.createAnswer(update);
            }
            case PUPIL -> {
                chatId = pupilService.findTutor(update);
                chatId = "203523943";
            } //ищется его тьютор
            case TUTOR -> {
                chatId = messageRepo.findFirstByConsumerChatIdOrderByIdDesc(sender.getChatId()).getReceiverChatId();
                text = sender.getUserName() + ": " + text;
            } // ищется последнее сообщение на которое он отвечает
            case PARENT -> {
                chatId = "203523943";
            }

            case ADMIN -> {
                chatId = "203523943";
            }

        }
        message.setChatId(chatId);
        String escapedMsg = text
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`");
        message.setText(escapedMsg);
        save(message, chatId);
        ArrayList<SendMessage> msgs = new ArrayList<>();
        msgs.add(message);
        SendMessage message1 = addAutoReplayToUnknown(update.getMessage().getChatId().toString());
        msgs.add(message1);
        return msgs;
    }



    private SendMessage addAutoReplayToUnknown(String chatId) {
        SendMessage message = new SendMessage();
        message.setText("Здравствуйте! К счастью, на нас обрушилась гигансткая волна учеников и мы не в состоянии предоставить услуги великолепного качества им всем! МЫ обязательно Вам напишем, когда нормально обучим достаточное количество тьюторов! Большое спасибо, что обратились! Мы очень рады, что Вы мне поверили!");
        message.setChatId(chatId);
        return message;
    }

    private Abonent saveSenderIfNeed(Abonent sender, Update update) {
        if (sender == null) {
            sender = new Abonent();
            sender.setChatId(update.getMessage().getChatId().toString());
            sender.setAbonentTypeEnum(AbonentTypeEnum.UNKNOWN);
            sender.setUserName(update.getMessage().getFrom().getUserName());
            sender.setFirstName(update.getMessage().getFrom().getFirstName());
            sender.setLastName(update.getMessage().getFrom().getLastName());
            abonentRepo.save(sender);
        }
        if (sender.getChatId() == null) {
            sender.setChatId(update.getMessage().getChatId().toString());
            abonentRepo.save(sender);
        }
        return sender;

    }
}
