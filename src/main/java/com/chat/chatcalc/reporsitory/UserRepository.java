package com.chat.chatcalc.reporsitory;

import com.chat.chatcalc.entiteis.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User ,String> {

//     User getUserById(String user_id);

}
