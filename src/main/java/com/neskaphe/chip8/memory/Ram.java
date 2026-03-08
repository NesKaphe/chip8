package com.neskaphe.chip8.memory;

public class Ram implements MemoryAccessor {

    private final byte[] bytes = new byte[4096];

    public void loadBytes(int startingAddress, byte[] bytes) {
        System.arraycopy(bytes, 0, this.bytes, startingAddress, bytes.length);
    }

    @Override
    public byte read(int address) {
        return bytes[address];
    }

    @Override
    public void write(int address, byte byteToWrite) {
        bytes[address] = byteToWrite;
    }
}
