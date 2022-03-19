package com.example.MongoJwt.dao;

import com.example.MongoJwt.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Userdao extends MongoRepository<User, String> {

    User findByUsername(String username);
}
