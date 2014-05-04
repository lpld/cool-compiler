package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.dfa.DfaBuilder;
import com.github.lpld.cool.lexing.automata.dfa.TransitionsTable;
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
        RegularExpression regex = new RegexParser("a|b").parse();

        Assert.assertNotNull(regex);

        TransitionsTable transitionsTable = new DfaBuilder(regex.buildAutomaton()).build();

        Assert.assertNotNull(transitionsTable);
    }
}
