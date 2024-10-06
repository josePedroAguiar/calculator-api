package com.wit.calculator.model;
import java.math.BigDecimal;

public class Result {
    private final BigDecimal result;

    public Result(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }
}