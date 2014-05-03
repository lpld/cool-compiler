package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.nfa.Automaton;
import lombok.RequiredArgsConstructor;

/**
 * @author leopold
 * @since 5/1/14
 */
@RequiredArgsConstructor
public class Iteration implements RegularExpression {
    private final RegularExpression underlying;

    @Override
    public Automaton buildAutomaton() {
        return underlying.buildAutomaton().iterate();
    }
}
