package com.chat.chatcalc.reporsitory;

import com.chat.chatcalc.entiteis.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductsRepository extends MongoRepository<Products, String> {

}