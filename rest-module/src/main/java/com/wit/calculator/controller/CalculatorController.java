package com.wit.calculator.controller;

import com.wit.calculator.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CalculatorController {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CalculatorController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/sum")
    public ResponseEntity<Result> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        String requestId = MDC.get("requestId");
        MessageProperties props = new MessageProperties();
        props.setHeader("requestId", requestId);
        Message message = new Message(("sum:" + a + "," + b).getBytes(), props);
        String result = (String) rabbitTemplate.convertSendAndReceive("calculator_queue", message);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }


    @GetMapping("/subtract")
    public ResponseEntity<Result> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        String requestId = MDC.get("requestId");
        MessageProperties props = new MessageProperties();
        props.setHeader("requestId", requestId);
        Message message = new Message(("subtract:" + a + "," + b).getBytes(), props);
        String result = (String) rabbitTemplate.convertSendAndReceive("calculator_queue", message);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }

    @GetMapping("/multiply")
    public ResponseEntity<Result> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        String requestId = MDC.get("requestId");
        MessageProperties props = new MessageProperties();
        props.setHeader("requestId", requestId);
        Message message = new Message(("multiply:" + a + "," + b).getBytes(), props);
        String result = (String) rabbitTemplate.convertSendAndReceive("calculator_queue", message);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }

    @GetMapping("/divide")
    public ResponseEntity<Result> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        String requestId = MDC.get("requestId");
        MessageProperties props = new MessageProperties();
        props.setHeader("requestId", requestId);
        Message message = new Message(("divide:" + a + "," + b).getBytes(), props);
        String result = (String) rabbitTemplate.convertSendAndReceive("calculator_queue", message);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }

    // Method to handle the response and return either result or error
    private ResponseEntity<Result> handleResponse(String response) {
        logger.info("Processing request");
        if (response.startsWith("Error")) {
            // Return error response
            try {
                return ResponseEntity.badRequest()
                        .header("Content-Type", "application/json")
                        .body(new Result(response));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Convert valid string response to BigDecimal and return result
        BigDecimal result = new BigDecimal(response);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }
}
