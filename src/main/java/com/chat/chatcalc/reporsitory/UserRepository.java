package com.chat.chatcalc.reporsitory;

import com.chat.chatcalc.entiteis.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository  extends MongoRepository<User ,String> {

    Optional<User> findByEmail(String username);

}
