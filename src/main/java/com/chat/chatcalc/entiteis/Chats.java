package com.chat.chatcalc.entiteis;

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
@Document(collection = "chats")
public class Chats {

    private String id;
    private String title;
    private String room_id;
    private List<Participants> participants = new ArrayList<>();
}
