package com.neskaphe.chip8.cpu;

import com.neskaphe.chip8.exception.InvalidOpCodeException;

public class InstructionDecoderImpl implements InstructionDecoder {

    private static final int OPCODE_JUMP_TYPE = 0x1;
    private static final int OPCODE_SKIP_EQUAL_TYPE = 0x3;
    private static final int OPCODE_SKIP_NOT_EQUAL_TYPE = 0x4;
    private static final int OPCODE_SKIP_EQUAL_V_REGISTERS_TYPE = 0x5;
    private static final int OPCODE_LOAD_TYPE = 0x6;
    private static final int OPCODE_ADD_BYTE_TYPE = 0x7;
    private static final int OPCODE_SKIP_NOT_EQUAL_V_REGISTERS_TYPE = 0x9;
    private static final int OPCODE_SET_I_REGISTER_TYPE = 0xA;
    private static final int OPCODE_JUMP_VO_ADDR_TYPE = 0xB;
    private static final int OPCODE_RANDOM_BYTE_KK_TYPE = 0xC;

    @Override
    public Instruction decode(Opcode opcode) {
        return switch (opcode.type()) {
            case OPCODE_JUMP_TYPE -> jumpInstruction(opcode);
            case OPCODE_SKIP_EQUAL_TYPE -> skipEqualInstruction(opcode);
            case OPCODE_SKIP_NOT_EQUAL_TYPE -> skipNotEqualInstruction(opcode);
            case OPCODE_SKIP_EQUAL_V_REGISTERS_TYPE -> skipEqualVRegistersInstruction(opcode);
            case OPCODE_LOAD_TYPE -> loadInstruction(opcode);
            case OPCODE_ADD_BYTE_TYPE -> addByteInstruction(opcode);
            case OPCODE_SKIP_NOT_EQUAL_V_REGISTERS_TYPE -> skipNotEqualVRegistersInstruction(opcode);
            case OPCODE_SET_I_REGISTER_TYPE -> setIRegisterInstruction(opcode);
            case OPCODE_JUMP_VO_ADDR_TYPE -> jumpV0AddrInstruction(opcode);
            case OPCODE_RANDOM_BYTE_KK_TYPE -> randomByteAndKkInstruction(opcode);
            default -> throw new InvalidOpCodeException(opcode);
        };
    }

    private static Instruction jumpInstruction(Opcode opcode) {
        return (cpu) -> cpu.setPc(opcode.nnn());
    }

    private static Instruction skipEqualInstruction(Opcode opcode) {
        return (cpu) -> {
            if ((cpu.getV(opcode.x()) & 0xFF) == opcode.kk()) {
                cpu.moveToNextInstruction();
            }
        };
    }

    private static Instruction skipNotEqualInstruction(Opcode opcode) {
        return (cpu) -> {
            if ((cpu.getV(opcode.x()) & 0xFF) != opcode.kk()) {
                cpu.moveToNextInstruction();
            }
        };
    }

    private static Instruction skipEqualVRegistersInstruction(Opcode opcode) {
        if(opcode.n() != 0) {
            throw new InvalidOpCodeException(opcode);
        }
        return (cpu) -> {
            if ((cpu.getV(opcode.x()) & 0xFF) == (cpu.getV(opcode.y()) & 0xFF)) {
                cpu.moveToNextInstruction();
            }
        };
    }

    private static Instruction loadInstruction(Opcode opcode) {
        return (cpu) -> cpu.setV(opcode.x(), (byte) opcode.kk());
    }

    private Instruction addByteInstruction(Opcode opcode) {
        int vRegisterIndex = opcode.x();
        return (cpu) -> {
            byte currentValueInRegister = cpu.getV(vRegisterIndex);
            cpu.setV(vRegisterIndex, (byte) (currentValueInRegister + opcode.kk()));
        };
    }

    private static Instruction skipNotEqualVRegistersInstruction(Opcode opcode) {
        if(opcode.n() != 0) {
            throw new InvalidOpCodeException(opcode);
        }
        return (cpu) -> {
            if ((cpu.getV(opcode.x()) & 0xFF) != (cpu.getV(opcode.y()) & 0xFF)) {
                cpu.moveToNextInstruction();
            }
        };
    }

    private static Instruction setIRegisterInstruction(Opcode opcode) {
        return cpu -> cpu.setI(opcode.nnn());
    }

    private static Instruction jumpV0AddrInstruction(Opcode opcode) {
        return cpu -> {
            int v0Value = cpu.getV(0) & 0xFF;
            cpu.setPc(v0Value + opcode.nnn());
        };
    }

    private static Instruction randomByteAndKkInstruction(Opcode opcode) {
        return cpu -> {
            cpu.setV(opcode.x(), (byte) (cpu.generateRandomByte() & opcode.kk()));
        };
    }
}
