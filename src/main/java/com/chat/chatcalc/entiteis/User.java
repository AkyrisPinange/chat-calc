package com.chat.chatcalc.entiteis;

import com.chat.chatcalc.entiteis.ChatReference;
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
@Document(collection = "user")
public class User {

    private String id;
    private String username;
    private List<ChatReference> chats = new ArrayList<>();
}
