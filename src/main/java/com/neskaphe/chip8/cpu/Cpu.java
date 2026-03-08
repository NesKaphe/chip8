package com.neskaphe.chip8.cpu;

import com.neskaphe.chip8.memory.MemoryAccessor;

public class Cpu {
    private final MemoryAccessor memory;
    private final InstructionDecoder decoder;

    private int pc;
    private final byte[] vRegister;

    public Cpu(MemoryAccessor memory, InstructionDecoder decoder, int initialPc) {
        this.memory = memory;
        this.decoder = decoder;
        this.pc = initialPc;
        vRegister = new byte[16];
    }

    public void step() {
        byte high = memory.read(pc);
        byte low = memory.read(pc + 1);

        Instruction instruction = decoder.decode(Opcode.fromBytes(high, low));
        pc += 2;
        instruction.execute(this);
    }

    int getPc() {
        return pc;
    }

    void setPc(int newPc) {
        this.pc = newPc;
    }

    void skipNextInstruction() {
        setPc(getPc() + 2);
    }

    byte getV(int vIndex) {
        return vRegister[vIndex];
    }

    void setV(int vIndex, byte value) {
        vRegister[vIndex] = value;
    }


}
