package com.neskaphe.chip8.cpu;

public class InstructionDecoderImpl implements InstructionDecoder {

    private static final int OPCODE_JUMP_TYPE = 0x1;
    private static final int OPCODE_LOAD_TYPE = 0x6;

    @Override
    public Instruction decode(Opcode opcode) {
        if(opcode.type() == OPCODE_JUMP_TYPE) {
            return (cpu) -> cpu.setPc(opcode.nnn());
        } else if(opcode.type() == OPCODE_LOAD_TYPE) {
            return (cpu) -> cpu.setV(opcode.x(), (byte) opcode.kk());
        }
        return null;
    }
}
