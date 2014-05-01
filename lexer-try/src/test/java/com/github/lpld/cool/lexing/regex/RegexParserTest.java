package com.github.lpld.cool.lexing.regex;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author leopold
 * @since 5/1/14
 */
public class RegexParserTest {
    @Test
    public void testParse() {
        RegularExpression regex = new RegexParser("(abc)").parse();

        Assert.assertNotNull(regex);
    }
}
