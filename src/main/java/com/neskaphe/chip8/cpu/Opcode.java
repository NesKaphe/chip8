package com.neskaphe.chip8.cpu;

public class Opcode {

    private final int value;

    private Opcode(int value) {
        this.value = value;
    }

    public static Opcode fromBytes(byte high, byte low) {
        return new Opcode((high & 0xFF) << 8 | low & 0xFF);
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

    public int getValue() {
        return value;
    }
}
