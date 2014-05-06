package com.github.lpld.cool.lexing.chars;

import java.util.Arrays;

/**
 * @author leopold
 * @since 5/2/14
 */
public class CharBuffer {
    private final char[] internalChars;
    private int position;
    private final int start;
    private final int end;


    public CharBuffer(String input) {
        this.internalChars = input.toCharArray();
        this.start = 0;
        this.position = 0;
        this.end = internalChars.length - 1;
    }

    private CharBuffer(char[] internalChars, int start, int end) {
        this.internalChars = internalChars;
        this.start = start;
        this.end = end;
        this.position = start;
    }

    public char charAt(int position) {
        // range check needed
        return internalChars[start + position];
    }

    public int length() {
        return end - start + 1;
    }

    public CharBuffer substring(int startPosition) {
        return new CharBuffer(internalChars, start + startPosition, end);
    }

    public CharBuffer substring(int startPosition, int endPosition) {
        return new CharBuffer(internalChars, start + startPosition, start + endPosition);
    }

    public char readNext() {
        return readNext(0);
    }

    public char readNext(int offset) {
        if (!hasChars(offset + 1)) {
            throw new IndexOutOfBoundsException(String.valueOf(position + offset));
        }
        return internalChars[position + offset];
    }

    public char popNext() {
        if (!hasNext()) {
            throw new IndexOutOfBoundsException(String.valueOf(position + 1));
        }
        return internalChars[position++];
    }

    public boolean hasNext() {
        return hasChars(1);
    }

    public boolean hasChars(int amount) {
        return end >= position + amount - 1;
    }

    @Override
    public String toString() {
        return String.copyValueOf(internalChars, start, length());
    }
}
