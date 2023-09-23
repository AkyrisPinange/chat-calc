package com.chat.chatcalc.entiteis;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection = "chats")
public class Chats {

    @Id
    private String id;
    private String roomName;
    private String roomId;
    private List<Participants> participants = new ArrayList<>();
    private List<ChatMessage> messages = new ArrayList<>();

    public Chats(String id, String roomName){
        this.id = id;
        this.roomName = roomName;
    }
    public Chats(String id, String roomName, String roomId, List<Participants> participants){
        this.id = id;
        this.roomName = roomName;
        this.roomId = roomId;
        this.participants = participants;
    }
}
