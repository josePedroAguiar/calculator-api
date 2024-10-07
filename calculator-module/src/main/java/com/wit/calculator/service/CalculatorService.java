package com.wit.calculator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);


    @RabbitListener(queues = "calculator_queue")
    public String calculate(Message message) {
        String requestId = (String) message.getMessageProperties().getHeaders().get("requestId");
        MDC.put("requestId", requestId);
        try {

            logger.info("Received message: {}");
            String content = new String(message.getBody());
            String[] parts = content.split(":");

            if (parts.length != 2) {
                return "Error: Invalid message format.";
            }

            String operation = parts[0];
            String[] numbers = parts[1].split(",");
            if (numbers.length != 2) {
                return "Error: Invalid message format.";
            }

            // !!! Redundant: Validate if inputs are integers
            // but in case the controller is changed, this is a good fallback
            if (!isInteger(numbers[0]) || !isInteger(numbers[1])) {

                return "Error: Both 'a' and 'b' must be integers.";
            }

            BigDecimal a = new BigDecimal(numbers[0]);
            BigDecimal b = new BigDecimal(numbers[1]);


            BigDecimal result;
            switch (operation) {
                case "sum":
                    result = a.add(b);
                    break;
                case "subtract":
                    result = a.subtract(b);
                    break;
                case "multiply":
                    result = a.multiply(b);
                    break;
                case "divide":
                    if (b.compareTo(BigDecimal.ZERO) == 0) {
                        return "Error: Division by zero is not allowed.";
                    }
                    result = a.divide(b, 10, RoundingMode.HALF_UP);
                    break;
                default:
                    // !!! Redundant: Return error message for unknown operation Because this is already handled in the controller
                    // but in case the controller is changed, this is a good fallback
                    return "Error: Unknown operation '" + operation + "'.";
            }


            return result.toString();

        } catch (Exception e) {

            return "Error: " + e.getMessage();
        }
    }

    private boolean isInteger(String str) {
        try {

            new BigDecimal(str).toBigIntegerExact();
            return true;
        } catch (ArithmeticException | NumberFormatException e) {
            return false;
        }
    }
}
