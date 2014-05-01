package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.Automaton;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author leopold
 * @since 5/1/14
 */
@RequiredArgsConstructor
public class Union implements RegularExpression {

    private final List<RegularExpression> underlyings;

    @Override
    public Automaton buildAutomaton() {

        Automaton result = null;

        for (RegularExpression underlying : underlyings) {
            Automaton automaton = underlying.buildAutomaton();
            if (result == null) {
                result = automaton;
            } else {
                result = result.union(automaton);
            }
        }

        return result;
    }
}
