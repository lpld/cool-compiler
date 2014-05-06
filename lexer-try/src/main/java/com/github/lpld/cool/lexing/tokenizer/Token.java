package com.github.lpld.cool.lexing.tokenizer;

import lombok.Data;

/**
 * @author leopold
 * @since 5/6/14
 */
@Data
public class Token {
    private final TokenClass tokenClass;
    private final String matchedString;
}
