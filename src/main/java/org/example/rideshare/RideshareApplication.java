package org.example.rideshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "org.example.rideshare")
@EnableMongoRepositories(basePackages = "org.example.rideshare.repository")
public class RideshareApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideshareApplication.class, args);
    }
}
