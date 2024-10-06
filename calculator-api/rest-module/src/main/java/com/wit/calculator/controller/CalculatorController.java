package com.wit.calculator.controller;

import com.wit.calculator.model.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CalculatorController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CalculatorController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/sum")
    public ResponseEntity<Result> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "sum:" + a + "," + b);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }

    @GetMapping("/subtract")
    public ResponseEntity<Result> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "subtract:" + a + "," + b);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }

    @GetMapping("/multiply")
    public ResponseEntity<Result> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "multiply:" + a + "," + b);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }

    @GetMapping("/divide")
    public ResponseEntity<Result> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        BigDecimal result = (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "divide:" + a + "," + b);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(new Result(result));
    }
}