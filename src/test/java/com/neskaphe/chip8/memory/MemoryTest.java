package com.neskaphe.chip8.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemoryTest {
    Ram ram;
    MemoryAccessor memoryAccessor;

    @BeforeEach
    void setUp() {
        ram = new Ram();
        memoryAccessor = ram;
    }

    @Test
    void shouldLoadBytesInRAM() {
        byte[] bytes = initializeRamWithBytes(0);
        byte result = memoryAccessor.read(0);

        assertEquals(bytes[0], result);
    }

    @Test
    void shouldReadByteAtOffset() {
        byte[] bytes = initializeRamWithBytes(0);
        byte result = memoryAccessor.read(1);

        assertEquals(bytes[1], result);
    }

    @Test
    void shouldLoadBytesAtOffset() {
        byte[] bytes = initializeRamWithBytes(10);
        byte result = memoryAccessor.read(11);

        assertEquals(bytes[1], result);
    }

    @Test
    void shouldWriteByte() {
        byte expectedByte = 0x6A;
        memoryAccessor.write(1, expectedByte);

        byte result = memoryAccessor.read(1);
        assertEquals(expectedByte, result);
    }

    private byte[] initializeRamWithBytes(int offset) {
        byte[] bytes = "toto".getBytes(Charset.defaultCharset());
        ram.loadBytes(offset, bytes);
        return bytes;
    }
}
