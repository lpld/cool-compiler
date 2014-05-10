package com.github.lpld.cool.lexing.tokenizer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author leopold
 * @since 5/5/14
 */
@Getter
@RequiredArgsConstructor
public enum SimpleTokenClass implements TokenClass {
    KEYWORD("if|else"),
    IDENTIFIER("[a-zA-Z]+"),
    DIGIT("[0-9]");

    private final String regex;


    @Override
    public String getClassName() {
        return name();
    }
}
