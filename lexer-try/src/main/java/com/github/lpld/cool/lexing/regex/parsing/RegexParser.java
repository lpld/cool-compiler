package com.github.lpld.cool.lexing.regex.parsing;

import com.github.lpld.cool.lexing.regex.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static final char OPEN_PAR = '(';
    private static final char CLOSE_PAR = ')';
    private static final char OPEN_BRACKET = '[';
    private static final char CLOSE_BRACKET = ']';
    private static final char SINGLE_QUOTE = '\'';
    private static final char UNION = '|';
    private static final char AT_LEASE_ONE = '+';
    private static final char OPTIONAL = '?';
    private static final char ITERATION = '*';


    public RegexParser(String input) {
        characters = new CharBuffer(input);
    }

    public RegularExpression parse() {
        return concatenate(parseGroup());
    }

    private RegularExpression concatenate(List<RegularExpression> regexes) {
        // if size == 0 ?
        if (regexes.size() == 1) {
            return regexes.get(0);
        } else {
            return new Concatenation(regexes);
        }
    }

    private RegularExpression union(List<RegularExpression> regexes) {
        // if size == 0 ?
        if (regexes.size() == 1) {
            return regexes.get(0);
        } else {
            return new Union(regexes);
        }
    }

    private RegularExpression parseUnionOrRange() {
        if (characters.hasChars(2) && characters.readNext(1) == '-') { // this won't work with escaped characters!
            return parseRange();
        } else {
            return union(parseGroup());
        }
    }

    private RegularExpression parseRange() {
        List<RegularExpression> range = new ArrayList<>();

        while (characters.hasNext()) {

            if (characters.readNext() == CLOSE_BRACKET || characters.readNext() == CLOSE_PAR) {
                // means end of group
                break;
            }

            // validation needed here
            char rangeStart = characters.popNext();
            popAndValidate('-');
            char rangeEnd = characters.popNext();

            if (rangeStart > rangeEnd) {
                throw new RegexParseException("Range start " + rangeStart + " is after range end " + rangeEnd);
            }


            for (char c = rangeStart; c <= rangeEnd; c++) {
                range.add(new SingleCharacter(String.valueOf(c)));
            }
        }

        if (range.size() == 1) {
            return range.get(0);
        } else {
            return new Union(range);
        }
    }

    private List<RegularExpression> parseGroup() {
        List<RegularExpression> regexes = new ArrayList<>();

        while (characters.hasNext()) {

            if (characters.readNext() == CLOSE_BRACKET || characters.readNext() == CLOSE_PAR) {
                // means end of group
                break;
            }

            char character = characters.popNext();

            switch (character) {
                case OPEN_PAR:
                    regexes.add(concatenate(parseGroup()));
                    popAndValidate(CLOSE_PAR);
                    break;

                case OPEN_BRACKET:
                    regexes.add(parseUnionOrRange());
                    popAndValidate(CLOSE_BRACKET);
                    break;

                case UNION:
                    // this can happen only when parsing concat group. additional check to be added
                    RegularExpression concat = concatenate(new ArrayList<>(regexes));
                    RegularExpression next = concatenate(parseGroup());

                    regexes.clear();
                    regexes.add(new Union(Arrays.asList(concat, next)));
                    break;

                case ITERATION:
                    // add check if it's not first in group
                    RegularExpression iterated = regexes.remove(regexes.size() - 1);
                    regexes.add(new Iteration(iterated));
                    break;

                case OPTIONAL:
                    RegularExpression optional = regexes.remove(regexes.size() - 1);
                    regexes.add(new Union(Arrays.asList(optional, new Epsilon())));
                    break;

                case AT_LEASE_ONE:
                    RegularExpression atLeastOne = regexes.remove(regexes.size() - 1);
                    regexes.add(new Concatenation(Arrays.asList(
                            atLeastOne,
                            new Iteration(atLeastOne)
                    )));
                    break;


                // ...
                default:
                    regexes.add(new SingleCharacter(String.valueOf(character)));
            }
        }

        return regexes;
    }

    private void popAndValidate(char c) {
        if (!characters.hasNext()) {
            throw new RegexParseException("Character '" + c + "' expected but end of string found");
        }

        char next = characters.popNext();

        if (next != c) {
            throw new RegexParseException("Character '" + c + "' expected but '" + next + "' found");
        }

    }
}
