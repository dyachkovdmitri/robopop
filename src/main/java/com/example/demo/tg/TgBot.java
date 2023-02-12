package com.example.demo.tg;


import com.example.demo.services.MessageService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TgBot extends TelegramLongPollingBot {

    MessageService service;

    public TgBot(
            TelegramBotsApi telegramBotsApi,
            MessageService service) throws TelegramApiException {

        this.service = service;
        telegramBotsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        String fileUrl = saveFile(update);
        List<SendMessage> sendMessages = service.prepareMessage(update, fileUrl);
        sendMsgs(sendMessages);
    }

    public synchronized void sendMsg(SendMessage message) {
        SendMessage sendMessage = new SendMessage();
        message.enableMarkdown(true);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMsgs(List<SendMessage> messages) {
        for (SendMessage message : messages) {
            sendMsg(message);
        }
    }

    private String saveFile(Update update) {
        if(update.getMessage().hasPhoto()){
            GetFile getFileRequest = new GetFile();
            getFileRequest.setFileId(update.getMessage().getPhoto().get(0).getFileId());
            try {
                org.telegram.telegrambots.meta.api.objects.File file = execute(getFileRequest);
                String fileUrl = "https://api.telegram.org/file/bot"+getBotToken()+"/"+file.getFilePath();
                return fileUrl;
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return "Robo_poppins_bot";
    }

    @Override
    public String getBotToken() {
        return "6062002125:AAEXAcVAD55W0gIDtMqRV1O42PRu2fPjV5M";
    }
}