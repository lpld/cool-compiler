package com.github.lpld.cool.lexing.regex.parsing;

import com.github.lpld.cool.lexing.regex.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple parser for simplified regex language.
 * <p/>
 * 'c' - a character
 * ab - concatenation
 * a|b, [ab] - union (or)
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

    private CharBuffer characters;
    private int pointer;

    private static final char OPEN_PAR = '(';
    private static final char CLOSE_PAR = ')';
    private static final char OPEN_BRACKET = '[';
    private static final char CLOSE_BRACKET = ']';
    private static final char SINGLE_QUOTE = '\'';
    private static final char UNION = '|';
    private static final char OPTIONAL = '?';
    private static final char ITERATION = '*';


    public RegexParser(String input) {
        characters = new CharBuffer(input);
    }

    public RegularExpression parse() {
        return parseConcatGroup();
    }

    private RegularExpression parseConcatGroup() {
        List<RegularExpression> regexes = parseGroup();
        // if size == 0 ?
        if (regexes.size() == 1) {
            return regexes.get(0);
        } else {
            return new Concatenation(regexes);
        }
    }

    private RegularExpression parseUnionGroup() {
        List<RegularExpression> regexes = parseGroup();
        // if size == 0 ?
        if (regexes.size() == 1) {
            return regexes.get(0);
        } else {
            return new Union(regexes);
        }
    }

    private List<RegularExpression> parseGroup() {
        List<RegularExpression> regexes = new ArrayList<>();
        boolean groupFinished = false;

        while (characters.hasNext() && !groupFinished) {
            char character = characters.readNext();

            switch (character) {
                case OPEN_PAR:
                    characters.popNext();  // OPEN_PAR
                    regexes.add(parseConcatGroup());
                    characters.popNext();  // SHOULD BE CLOSE_PAR, validation needed here
                    break;

                case OPEN_BRACKET:
                    characters.popNext();  // OPEN_BRACKET
                    regexes.add(parseUnionGroup());
                    characters.popNext();  // SHOULD BE CLOSE_BRACKET, validation needed here
                    break;

                case ITERATION:
                    characters.popNext(); // *
                    // add check if it's not first in group
                    RegularExpression iterated = regexes.remove(regexes.size() - 1);
                    regexes.add(new Iteration(iterated));
                    break;

                case CLOSE_PAR:case CLOSE_BRACKET:
                    groupFinished = true;
                    break;

                // ...
                default:
                    regexes.add(aSymbol());
            }
        }

        return regexes;
    }

    private RegularExpression aSymbol() {
        char character = characters.popNext();
        return new SingleCharacter(String.valueOf(character));
    }
}
