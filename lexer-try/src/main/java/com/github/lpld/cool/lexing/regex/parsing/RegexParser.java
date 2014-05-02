package com.github.lpld.cool.lexing.regex.parsing;

import com.github.lpld.cool.lexing.regex.Concatenation;
import com.github.lpld.cool.lexing.regex.RegularExpression;
import com.github.lpld.cool.lexing.regex.SingleCharacter;

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

    private CharBuffer characters;
    private int pointer;

    private static final char OPEN_PAR = '(';
    private static final char CLOSE_PAR = ')';
    private static final char SINGLE_QUOTE = '\'';
    private static final char UNION = '|';
    private static final char OPTIONAL = '?';
    private static final char ITERATION = '*';



    public RegexParser(String input) {
        characters = new CharBuffer(input);
    }

    public RegularExpression parse() {
        return parseGroup();
    }

    private RegularExpression parseGroup() {
        List<RegularExpression> concats = new ArrayList<>();
        boolean groupFinished = false;

        while (characters.hasNext() && !groupFinished) {
            char character = characters.readNext();

            switch (character) {
                case OPEN_PAR:
                    concats.add(parseEmbeddedGroup());
                    break;

                case CLOSE_PAR:
                    groupFinished = true;
                    break;

                // ...
                default:
                    concats.add(aSymbol());
            }
        }

        // if size == 0 ?
        if (concats.size() == 1) {
            return concats.get(0);
        } else {
            return new Concatenation(concats);
        }
    }

    private RegularExpression parseEmbeddedGroup() {
        characters.popNext();  // OPEN_PAR

        RegularExpression embedded = parseGroup();

        characters.popNext(); // SHOULD BE CLOSE_PAR, need size checking here
        return embedded;
    }

    private RegularExpression aSymbol() {
        char character = characters.popNext();
        return new SingleCharacter(String.valueOf(character));

    }
}
