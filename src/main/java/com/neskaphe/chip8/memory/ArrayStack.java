package com.neskaphe.chip8.memory;

import com.neskaphe.chip8.exception.StackOverflowException;
import com.neskaphe.chip8.exception.StackUnderflowException;

public class ArrayStack implements Stack {

    private static final int MAX_SIZE = 16;

    private final int[] elements = new int[MAX_SIZE];
    private int size = 0;

    @Override
    public int pop() {
        if(size == 0) {
            throw new StackUnderflowException("Can't pop from an empty stack");
        }

        return elements[--size];
    }

    @Override
    public void push(int element) {
        if(size == MAX_SIZE) {
            throw new StackOverflowException("Can't push on a full stack [maxSize: 16]");
        }
        this.elements[size++] = element;
    }
}
