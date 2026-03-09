package com.neskaphe.chip8.exception;

public class StackUnderflowException extends RuntimeException {

    public StackUnderflowException(String message) {
        super(message);
    }
}
