package com.neskaphe.chip8.exception;

import com.neskaphe.chip8.cpu.Opcode;

public class InvalidOpCodeException extends RuntimeException {

    public InvalidOpCodeException(Opcode opcode) {
        super(String.format("Opcode : 0x%04X invalid", opcode.getValue()));
    }

}
