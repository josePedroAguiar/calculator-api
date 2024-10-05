package com.wit.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestModuleApplication {

    public static void main(String[] args) {
        System.setProperty("server.port", "8080");
        SpringApplication.run(RestModuleApplication.class, args);
    }

}
