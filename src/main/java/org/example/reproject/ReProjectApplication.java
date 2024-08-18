package org.example.reproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ReProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReProjectApplication.class, args);
    }

}
