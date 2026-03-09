package com.neskaphe.chip8.exception;

public class StackOverflowException extends RuntimeException {

    public StackOverflowException(String message) {
        super(message);
    }
}
