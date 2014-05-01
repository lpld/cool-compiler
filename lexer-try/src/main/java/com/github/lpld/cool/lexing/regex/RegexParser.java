package com.github.lpld.cool.lexing.regex;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple parser for simplified regex language.
 * <p>
 * 'c' - a character
 * ab - concatenation
 * a|b - union (or)
 * a* - iteration
 * a+ = aa* (at least one)
 * [a-z] - range
 * a? = a + '' (optional)
 * () can be used for groups
 * \ can be used to escape characters +,|,\,*,-,?,(,)
 *
 * @author leopold
 * @since 5/1/14
 */
public class RegexParser {

    private char[] characters;
    private int pointer;

    private static final char OPEN_PAR = '(';
    private static final char CLOSE_PAR = ')';
    private static final char SINGLE_QUOTE = '\'';
    private static final char UNION = '|';
    private static final char OPTIONAL = '?';
    private static final char ITERATION = '*';

    private RegularExpression current;

    public RegexParser(String input) {
        characters = input.toCharArray();
        pointer = 0;
    }

    public RegularExpression parse() {
        startGroup();

        return current;
    }

    private void startGroup() {

        List<Character> group = new ArrayList<>();

        while (hasNext()) {
            switch (readNext()) {
                case OPEN_PAR:
                    startEmbeddedGroup();
                    break;

                case CLOSE_PAR:
                    return;

                default:
                    group.add(popNext());
            }
        }

        List<RegularExpression> regexes = new ArrayList<>();

        for (Character character : group) {
            regexes.add(new SingleCharacter(character.toString()));
        }



        current = new Union(regexes);
    }

    private void startEmbeddedGroup() {
        popNext(); // '('

        while (hasNext()) {
            startGroup();

            char character = popNext();

            if (character != CLOSE_PAR) {
                throw new IllegalArgumentException("group should be closed by ')'");
            }
        }
    }

    private boolean hasNext() {
        return pointer < characters.length;
    }

    private char readNext() {
        return characters[pointer];
    }

    private char popNext() {
        return characters[pointer++];
    }
}
