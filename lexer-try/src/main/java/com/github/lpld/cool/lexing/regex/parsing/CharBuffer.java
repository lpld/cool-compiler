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
        return characters[position];
    }

    public char popNext() {
        return characters[position++];
    }

    public boolean hasNext() {
        return characters.length > position;
    }
}
