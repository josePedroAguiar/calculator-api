package com.wit.calculator.model;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Result {

    private static final Logger logger = LoggerFactory.getLogger(Result.class);

    private BigDecimal result;

    public Result(String result) {

        logger.trace("Processing optional result: {}", result);
        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException("Result string cannot be null or empty");
        }

        if (result.startsWith("Error: Division ")) {
            String[]message=result.split(":");
            throw new ArithmeticException(message[1]);
        }


        this.result = new BigDecimal(result);
    }

    // Constructor for successful result
    public Result(BigDecimal result) {
        this.result = result;  // Direct assignment
    }

    // Getter for result
    public BigDecimal getResult() {
        return result;
    }

    // Method to check if result is present
    public boolean hasValue() {
        return result != null;
    }
}
