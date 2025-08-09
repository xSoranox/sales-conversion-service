package com.example.sales.exception;

public class InvalidSalesDataException extends RuntimeException {
    public InvalidSalesDataException(String message) {
        super(message);
    }
}

