package com.github.lpld.cool.lexing.regex.parsing;

/**
 * @author leopold
 * @since 5/2/14
 */
public class RegexParseException extends RuntimeException {
    public RegexParseException(String message) {
        super(message);
    }

    public RegexParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
