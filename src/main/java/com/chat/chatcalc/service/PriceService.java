package com.chat.chatcalc.service;


import com.chat.chatcalc.entiteis.ChatMessage;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

 public ChatMessage calcPrice(ChatMessage chatMessage){
     String[] content = chatMessage.getContent().toString().split("-");
     Double  total = Double.valueOf(content[3]);
     Double value = Double.valueOf( content[2]) * Integer.parseInt(content[1]);
     total = total + value;
     chatMessage.setContent(content[0].toString() + " - " +  content[2]+ " - " + total );
     return  chatMessage;
 }

}
