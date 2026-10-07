package com.itzhy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CampusLabManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusLabManagementApplication.class, args);
    }

}
