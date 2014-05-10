package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.tokenizer.SimpleTokenClass;
import com.github.lpld.cool.lexing.tokenizer.Token;
import com.github.lpld.cool.lexing.tokenizer.Tokenizer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author leopold
 * @since 5/6/14
 */
public class TokenizerTest {
    @Test
    public void test() {
        Tokenizer tokenizer = new Tokenizer(Arrays.asList(SimpleTokenClass.values()), "if6elser7else");

        List<Token> tokens = tokenizer.getTokens();

        assertEquals(5, tokens.size());

        assertEquals(SimpleTokenClass.KEYWORD, tokens.get(0).getTokenClass());
        assertEquals("if", tokens.get(0).getMatchedString());

        assertEquals(SimpleTokenClass.DIGIT, tokens.get(1).getTokenClass());
        assertEquals("6", tokens.get(1).getMatchedString());

        assertEquals(SimpleTokenClass.IDENTIFIER, tokens.get(2).getTokenClass());
        assertEquals("elser", tokens.get(2).getMatchedString());

        assertEquals(SimpleTokenClass.DIGIT, tokens.get(3).getTokenClass());
        assertEquals("7", tokens.get(3).getMatchedString());

        assertEquals(SimpleTokenClass.KEYWORD, tokens.get(4).getTokenClass());
        assertEquals("else", tokens.get(4).getMatchedString());

    }

}
