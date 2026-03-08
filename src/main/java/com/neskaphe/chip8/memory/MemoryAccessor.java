package com.neskaphe.chip8.memory;

public interface MemoryAccessor {
    byte read(int address);

    void write(int address, byte byteToWrite);
}
