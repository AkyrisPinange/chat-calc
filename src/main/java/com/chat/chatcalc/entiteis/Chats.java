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
@Document(collection = "chats")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Chats {

    @Id
    private String id;
    private String title;
    private String roomId;
    private List<Participants> participants = new ArrayList<>();

    public Chats(String id, String title){
        this.id = id;
        this.title = title;
    }
}
