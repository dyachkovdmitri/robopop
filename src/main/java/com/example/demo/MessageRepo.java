package com.example.demo;

import com.example.demo.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {
    Message findFirstByConsumerChatIdOrderByIdDesc(String chatId);
}
