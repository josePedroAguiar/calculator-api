package com.wit.calculator.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {

    @RabbitListener(queues = "calculator_queue")
    public BigDecimal calculateOperation(String message) {
        String[] parts = message.split(":");
        String operation = parts[0];
        String[] numbers = parts[1].split(",");
        BigDecimal a = new BigDecimal(numbers[0]);
        BigDecimal b = new BigDecimal(numbers[1]);

        switch (operation) {
            case "sum":
                return a.add(b);
            case "subtract":
                return a.subtract(b);
            case "multiply":
                return a.multiply(b);
            case "divide":
                if (b.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a.divide(b, 10, RoundingMode.HALF_UP);
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}