package com.neskaphe.chip8.cpu;

import com.neskaphe.chip8.memory.MemoryAccessor;

import java.util.Random;

public class Cpu {
    private static final int PC_STEP = 2;

    private final MemoryAccessor memory;
    private final InstructionDecoder decoder;
    private final Random random;

    private int pc;
    private final byte[] vRegister;
    private int iRegister;

    public Cpu(MemoryAccessor memory, InstructionDecoder decoder, Random random, int initialPc) {
        this.memory = memory;
        this.decoder = decoder;
        this.random = random;
        this.pc = initialPc;
        vRegister = new byte[16];
    }

    public void step() {
        byte high = memory.read(pc);
        byte low = memory.read(pc + 1);

        Instruction instruction = decoder.decode(Opcode.fromBytes(high, low));

        moveToNextInstruction();

        instruction.execute(this);
    }

    int getPc() {
        return pc;
    }

    void setPc(int newPc) {
        this.pc = newPc;
    }

    void moveToNextInstruction() {
        setPc(getPc() + PC_STEP);
    }

    byte getV(int vIndex) {
        return vRegister[vIndex];
    }

    void setV(int vIndex, byte value) {
        vRegister[vIndex] = value;
    }

    int getI() {
        return iRegister;
    }

    void setI(int value) {
        this.iRegister = value;
    }

    byte generateRandomByte() {
        return (byte) random.nextInt(256);
    }
}
