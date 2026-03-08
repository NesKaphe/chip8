package com.neskaphe.chip8.cpu;

public interface InstructionDecoder {
    Instruction decode(Opcode opcode);
}
