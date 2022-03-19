package com.example.MongoJwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;
import java.util.Date;

@SpringBootApplication
public class MongoJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoJwtApplication.class, args);

	}

}
