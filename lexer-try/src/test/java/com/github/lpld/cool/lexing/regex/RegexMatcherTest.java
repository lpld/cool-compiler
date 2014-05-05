package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.regex.matching.RegexMatcher;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author leopold
 * @since 5/5/14
 */
public class RegexMatcherTest {
    @Test
    public void testMatching() {
        RegexMatcher aOrB = new RegexMatcher("a|b");
        assertTrue(aOrB.matches("a"));
        assertTrue(aOrB.matches("b"));
        assertFalse(aOrB.matches("ab"));

        RegexMatcher r = new RegexMatcher("[abc]+(qw)*");
        assertTrue(r.matches("bbccaa"));
        assertFalse(r.matches("qw"));
        assertTrue(r.matches("aqw"));
        assertTrue(r.matches("bqwqw"));
        assertFalse(r.matches("cqwq"));
    }
}
