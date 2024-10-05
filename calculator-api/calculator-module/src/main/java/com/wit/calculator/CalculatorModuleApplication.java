package com.wit.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculatorModuleApplication {

    public static void main(String[] args)
    {
        System.setProperty("server.port", "8081");
        SpringApplication.run(CalculatorModuleApplication.class, args);
    }

}
