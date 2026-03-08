package com.neskaphe.chip8.cpu;

public class InstructionDecoderImpl implements InstructionDecoder {

    private static final int OPCODE_JUMP_TYPE = 0x1;
    private static final int OPCODE_SKIP_EQUAL_TYPE = 0x3;
    private static final int OPCODE_SKIP_NOT_EQUAL_TYPE = 0x4;
    private static final int OPCODE_LOAD_TYPE = 0x6;

    private static final int UNSIGNED_FLAG = 0xFF;

    @Override
    public Instruction decode(Opcode opcode) {
        return switch (opcode.type()) {
            case OPCODE_JUMP_TYPE -> jumpInstruction(opcode);
            case OPCODE_SKIP_EQUAL_TYPE -> skipEqualInstruction(opcode);
            case OPCODE_SKIP_NOT_EQUAL_TYPE -> skipNotEqualInstruction(opcode);
            case OPCODE_LOAD_TYPE -> loadInstruction(opcode);
            default -> throw new IllegalArgumentException(String.format("Opcode : 0x%04X invalid", opcode.getValue()));
        };
    }

    private static Instruction jumpInstruction(Opcode opcode) {
        return (cpu) -> cpu.setPc(opcode.nnn());
    }

    private static Instruction skipEqualInstruction(Opcode opcode) {
        return (cpu) -> {
            if ((cpu.getV(opcode.x()) & UNSIGNED_FLAG) == opcode.kk()) {
                cpu.setPc(cpu.getPc() + 2);
            }
        };
    }

    private static Instruction skipNotEqualInstruction(Opcode opcode) {
        return (cpu) -> {
            if ((cpu.getV(opcode.x()) & UNSIGNED_FLAG) != opcode.kk()) {
                cpu.setPc(cpu.getPc() + 2);
            }
        };
    }

    private static Instruction loadInstruction(Opcode opcode) {
        return (cpu) -> cpu.setV(opcode.x(), (byte) opcode.kk());
    }
}
