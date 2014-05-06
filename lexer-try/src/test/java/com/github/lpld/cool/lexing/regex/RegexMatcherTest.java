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
        assertTrue(aOrB.match("a"));
        assertTrue(aOrB.match("b"));
        assertFalse(aOrB.match("ab"));

        RegexMatcher r = new RegexMatcher("[abc]+(qw)*");
        assertTrue(r.match("bbccaa"));
        assertFalse(r.match("qw"));
        assertTrue(r.match("aqw"));
        assertTrue(r.match("bqwqw"));
        assertFalse(r.match("cqwq"));
    }
}
