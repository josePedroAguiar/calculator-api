package com.wit.calculator;


import com.wit.calculator.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorModuleApplicationTests {

    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    public void testCalculateSum() {
        Message message = createMessage("sum:3,5", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("8", result);
    }

    @Test
    public void testCalculateSubtract() {
        Message message = createMessage("subtract:10,4", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("6", result);
    }

    @Test
    public void testCalculateMultiply() {
        Message message = createMessage("multiply:2,3", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("6", result);
    }

    @Test
    public void testCalculateDivide() {
        Message message = createMessage("divide:10,2", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("5", result);
    }

    @Test
    public void testCalculateDivideByZero() {
        Message message = createMessage("divide:10,0", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("Error: Division by zero is not allowed.", result);
    }

    @Test
    public void testCalculateUnknownOperation() {
        Message message = createMessage("unknown:10,2", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("Error: Unknown operation 'unknown'.", result);
    }

    @Test
    public void testCalculateInvalidInput() {
        Message message = createMessage("sum:a,b", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("Error: Both 'a' and 'b' must be integers.", result);
    }

    @Test
    public void testCalculateWithMissingArgument() {
        Message message = createMessage("divide:10,", "test-request-id");
        String result = calculatorService.calculate(message);
        assertEquals("Error: Invalid message format.", result); // Adjusted for consistent error handling
    }

    private Message createMessage(String body, String requestId) {
        MessageProperties props = new MessageProperties();
        props.setHeader("requestId", requestId);
        return new Message(body.getBytes(), props);
    }
}
