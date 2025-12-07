package org.example.rideshare.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

@Configuration
public class MongoTestRunner {


    @Bean
    public ApplicationRunner testMongo(MongoTemplate mongoTemplate){
        return args -> {
            System.out.println("Connected to DB: " + mongoTemplate.getDb().getName());
            System.out.println("Collections " + mongoTemplate.getDb().listCollectionNames().into(new java.util.ArrayList<>()));
        };

    }
}
