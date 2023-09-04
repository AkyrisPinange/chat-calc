package com.chat.chatcalc.reporsitory;


import com.chat.chatcalc.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
}
