package com.github.lpld.cool.lexing.tokenizer;

import com.github.lpld.cool.lexing.chars.CharBuffer;
import com.github.lpld.cool.lexing.regex.matching.RegexMatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leopold
 * @since 5/5/14
 */
public class Tokenizer {
    private final Map<TokenClass, RegexMatcher> tokenClasses = new LinkedHashMap<>();
    private CharBuffer input;
    private List<Token> tokens = new ArrayList<>();

    public Tokenizer(List<? extends TokenClass> tokenClasses, String input) {
        for (TokenClass tokenClass : tokenClasses) {
            this.tokenClasses.put(tokenClass, new RegexMatcher(tokenClass.getRegex()));
        }
        this.input = new CharBuffer(input);
    }

    public List<Token> getTokens() {
        while (matchToken()) {}

        return tokens;
    }

    private boolean matchToken() {
        Token matched = null;

        for (Map.Entry<TokenClass, RegexMatcher> entry : tokenClasses.entrySet()) {
            int matchIndex = entry.getValue().match(input);

            if (matchIndex >= 0) {
                if (matched == null || matched.getMatchedString().length() < matchIndex + 1) {
                    String matchedString = input.substring(0, matchIndex).toString();
                    matched = new Token(entry.getKey(), matchedString);
                }
            }
        }


        if (matched == null) {
            return false;
        } else {
            input = input.substring(matched.getMatchedString().length());
            tokens.add(matched);
            return true;
        }
    }
}
