package com.chat.chatcalc.reporsitory;

import com.chat.chatcalc.entiteis.Chats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatsRepository extends MongoRepository<Chats,String> {
}
