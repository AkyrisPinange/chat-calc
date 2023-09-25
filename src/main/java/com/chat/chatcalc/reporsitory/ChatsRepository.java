package com.chat.chatcalc.reporsitory;

import com.chat.chatcalc.entiteis.Chats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatsRepository extends MongoRepository<Chats,String> {

    Chats getChatByRoomId(String roomId);

    boolean  existsByRoomId(String roomId);

    @Query(value = "{'roomId': ?0, 'participants.userId': ?1}")
    Chats findByRoomIdAndParticipantUserId(String roomId, String userId);

    @Query(value = "{'participants.userId': ?0}", fields = "{'_id': 1, 'roomName': 1, 'roomId': 1}")
    List<Chats> findAllByParticipantsUserId(String userId);


}
