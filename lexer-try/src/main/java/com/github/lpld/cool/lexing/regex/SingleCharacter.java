package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.nfa.Automaton;
import com.github.lpld.cool.lexing.automata.nfa.State;
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

        State end = State.create();
        State start = State.createWithTransition(c, end);

        return new Automaton(start, end);
    }
}
