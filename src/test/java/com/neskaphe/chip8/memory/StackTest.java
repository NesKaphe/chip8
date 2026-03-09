package com.neskaphe.chip8.memory;

import com.neskaphe.chip8.exception.StackOverflowException;
import com.neskaphe.chip8.exception.StackUnderflowException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackTest {

    Stack stack = new ArrayStack();

    @Test
    void shouldThrowUnderflowWhenStackIsEmpty() {
        var ex = assertThrows(StackUnderflowException.class, stack::pop);

        assertEquals("Can't pop from an empty stack", ex.getMessage());
    }

    @Test
    void shouldReturnPushedValueAfterOnePush() {
        stack.push(1);

        assertEquals(1, stack.pop());
    }

    @Test
    void shouldReturnLastPushedValue() {
        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.pop());
    }

    @Test
    void shouldPopValuesInReversePushOrder() {
        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void shouldThrowOverflowWhenStackContainsAlready16Elements() {
        for(var i = 0; i < 16; i++) {
            stack.push(i);
        }

        var ex = assertThrows(StackOverflowException.class, () -> stack.push(17));

        assertEquals("Can't push on a full stack [maxSize: 16]", ex.getMessage());
    }
}
