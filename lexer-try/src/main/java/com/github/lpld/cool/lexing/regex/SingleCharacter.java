package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.Automaton;
import com.github.lpld.cool.lexing.automata.State;
import lombok.RequiredArgsConstructor;

/**
 * @author leopold
 * @since 5/1/14
 */
@RequiredArgsConstructor
public class SingleCharacter implements RegularExpression {
    private final String c;

    @Override
    public Automaton buildAutomaton() {
        State start = State.create();
        State end = State.create();

        start.addTransition(c, end);

        return new Automaton(start, end);
    }
}
