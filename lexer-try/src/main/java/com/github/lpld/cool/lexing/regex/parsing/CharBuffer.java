package com.github.lpld.cool.lexing.regex.parsing;

/**
 * @author leopold
 * @since 5/2/14
 */
public class CharBuffer {
    private final char[] characters;
    private int position;

    public CharBuffer(String input) {
        this(input.toCharArray(), 0);
    }

    private CharBuffer(char[] characters, int position) {
        this.characters = characters;
        this.position = position;
    }

    public char readNext() {
        return readNext(0);
    }

    public char readNext(int offset) {
        return characters[position + offset];
    }

    public char popNext() {
        return characters[position++];
    }

    public boolean hasNext() {
        return hasChars(1);
    }

    public boolean hasChars(int amount) {
        return characters.length > position + amount - 1;
    }
}
