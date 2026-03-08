package com.neskaphe.chip8.cpu;

public class Opcode {

    public static final int UNSIGNED_FLAG = 0xFF;
    private final int value;

    private Opcode(int value) {
        this.value = value;
    }

    public static Opcode fromBytes(byte high, byte low) {
        return new Opcode((high & UNSIGNED_FLAG) << 8 | low & UNSIGNED_FLAG);
    }

    public int type() {
        return (value & 0xF000) >> 12;
    }

    int x() {
        return (value & 0x0F00) >> 8;
    }

    int y() {
        return (value & 0x00F0) >> 4;
    }

    int n() {
        return value & 0x000F;
    }

    int kk() {
        return value & 0x00FF;
    }

    int nnn() {
        return value & 0x0FFF;
    }
}
