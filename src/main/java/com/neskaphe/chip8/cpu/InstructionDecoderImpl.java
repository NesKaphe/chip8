package com.neskaphe.chip8.cpu;

public class InstructionDecoderImpl implements InstructionDecoder {

    public static final int OPCODE_LOAD_TYPE = 0x6;

    @Override
    public Instruction decode(Opcode opcode) {
        if(opcode.type() == OPCODE_LOAD_TYPE) {
            return (cpu) -> cpu.setV(opcode.x(), (byte) opcode.kk());
        }
        return null;
    }
}
