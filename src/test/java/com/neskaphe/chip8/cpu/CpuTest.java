package com.neskaphe.chip8.cpu;

import com.neskaphe.chip8.memory.Ram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CpuTest {

    //No op opération from opcode
    public static final InstructionDecoder DECODER_NO_OP_INSTRUCTION = (opcode) -> (cpu) -> {};

    private Ram ram;
    private Cpu cpu;

    @BeforeEach
    void setUp() {
        ram = new Ram();
        cpu = new Cpu(ram, new InstructionDecoderImpl(), new Random(), 0x200);
    }

    @Test
    void shouldStartAtDecidedPc() {
        int initialPc = 0x200;
        Cpu cpu = new Cpu(ram, DECODER_NO_OP_INSTRUCTION, new Random(), initialPc);

        assertEquals(initialPc, cpu.getPc());
    }

    @Test
    void shouldIncrementPCAfterInstruction() {
        Cpu cpu = new Cpu(ram, DECODER_NO_OP_INSTRUCTION, new Random(), 0x200);

        int pcBeforeStep = cpu.getPc();
        cpu.step();

        assertEquals(pcBeforeStep + 2, cpu.getPc());
    }

    @Test
    void shouldHavePcFromInstructionWhenModifiedDuringExecution() {
        int jumpToPc = 0x300;
        InstructionDecoder decoder = (opcode -> (cpu) -> cpu.setPc(jumpToPc));
        Cpu cpu = new Cpu(ram, decoder, new Random(), 0x200);

        cpu.step();

        assertEquals(jumpToPc, cpu.getPc());
    }

    @Test
    void shouldExecute1nnnInstruction() {
        ram.loadBytes(0x200, new byte[] {0x13, 0x21});

        cpu.step();

        assertEquals(0x321, cpu.getPc());
    }

    @Test
    void shouldExecute3xkkInstruction_whenVEqualsKk() {
        ram.loadBytes(0x200, new byte[] {0x30, 0x7F});
        cpu.setV(0, (byte) 0x7F);

        cpu.step();

        assertEquals(0x204, cpu.getPc());
    }

    @Test
    void shouldExecute3xkkInstruction_whenVNotEqualsKk() {
        ram.loadBytes(0x200, new byte[] {0x30, 0x7F});

        cpu.step();

        assertEquals(0x202, cpu.getPc());
    }

    @Test
    void shouldExecute4xkkInstruction_whenVEqualsKk() {
        ram.loadBytes(0x200, new byte[] {0x41, 0x7F});
        cpu.setV(1, (byte) 0x7F);

        cpu.step();

        assertEquals(0x202, cpu.getPc());
    }

    @Test
    void shouldExecute4xkkInstruction_whenVNotEqualsKk() {
        ram.loadBytes(0x200, new byte[] {0x41, 0x7F});

        cpu.step();

        assertEquals(0x204, cpu.getPc());
    }

    @Test
    void shouldExecute5xy0Instruction_whenVxEqualsVy() {
        ram.loadBytes(0x200, new byte[] {0x52, 0x30});
        cpu.setV(2, (byte) 0x7F);
        cpu.setV(3, (byte) 0x7F);

        cpu.step();

        assertEquals(0x204, cpu.getPc());
    }

    @Test
    void shouldExecute5xy0Instruction_whenVxNotEqualsVy() {
        ram.loadBytes(0x200, new byte[] {0x52, 0x30});
        cpu.setV(2, (byte) 0x7F);
        cpu.setV(3, (byte) 0xF7);

        cpu.step();

        assertEquals(0x202, cpu.getPc());
    }

    @Test
    void shouldExecute6xkkInstruction() {
        byte byteInRegister = 0x0F;
        ram.loadBytes(0x200, new byte[] {0x6F, byteInRegister}); //6F0F, LD Vx, byte

        cpu.step();

        assertEquals(byteInRegister, cpu.getV(0xF));
    }

    @Test
    void shouldExecute7xkkInstruction() {
        ram.loadBytes(0x200, new byte[] {0x75, (byte) 0xF2});
        cpu.setV(5, (byte) 0x0F);

        cpu.step();

        assertEquals(1, cpu.getV(5));
    }

    @Test
    void shouldExecute9xy0Instruction_whenVxEqualsVy() {
        ram.loadBytes(0x200, new byte[] {(byte) 0x92, 0x30});
        cpu.setV(2, (byte) 0x7F);
        cpu.setV(3, (byte) 0x7F);

        cpu.step();

        assertEquals(0x202, cpu.getPc());
    }

    @Test
    void shouldExecute9xy0Instruction_whenVxNotEqualsVy() {
        ram.loadBytes(0x200, new byte[] {(byte) 0x92, 0x30});
        cpu.setV(2, (byte) 0x7F);
        cpu.setV(3, (byte) 0xF7);

        cpu.step();

        assertEquals(0x204, cpu.getPc());
    }

    @Test
    void shouldExecuteAnnnInstruction() {
        ram.loadBytes(0x200, new byte[] {(byte) 0xA4, 0x51});

        cpu.step();

        assertEquals(0x451, cpu.getI());
    }

    @Test
    void shouldExecuteBnnnInstruction() {
        ram.loadBytes(0x200, new byte[] {(byte) 0xB4, 0x52});
        cpu.setV(0, (byte) 0x04);

        cpu.step();

        assertEquals(0x456, cpu.getPc());
    }

    @Test
    void shouldExecuteCxkkInstruction() {
        ram.loadBytes(0x200, new byte[] {(byte) 0xC1, (byte) 0xF0});
        Random seededRandom = new Random(42); //Va produire 186 (0xBA)
        cpu = new Cpu(ram, new InstructionDecoderImpl(), seededRandom, 0x200);

        cpu.step();

        int unsignedResult = cpu.getV(1) & 0xFF;
        assertEquals(0xB0, unsignedResult);
    }
}
