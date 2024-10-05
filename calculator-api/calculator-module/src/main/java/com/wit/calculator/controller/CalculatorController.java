package com.wit.calculator.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BigDecimal sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "sum:" + a + "," + b);
    }

    @GetMapping("/subtract")
    public BigDecimal subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "subtract:" + a + "," + b);
    }

    @GetMapping("/multiply")
    public BigDecimal multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "multiply:" + a + "," + b);
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return (BigDecimal) rabbitTemplate.convertSendAndReceive("calculator_queue", "divide:" + a + "," + b);
    }
}