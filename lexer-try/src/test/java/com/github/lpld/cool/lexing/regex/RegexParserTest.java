package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.CompoundState;
import com.github.lpld.cool.lexing.automata.DfaBuilder;
import com.github.lpld.cool.lexing.regex.parsing.RegexParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author leopold
 * @since 5/1/14
 */
public class RegexParserTest {
    @Test
    public void testParse() {
        RegularExpression regex = new RegexParser("[10]*1").parse();

        Assert.assertNotNull(regex);

        CompoundState compoundState = new DfaBuilder().build(regex.buildAutomaton());

        Assert.assertNotNull(compoundState);
    }
}
