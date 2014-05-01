package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.Automaton;
import com.github.lpld.cool.lexing.automata.State;

/**
 * @author leopold
 * @since 5/1/14
 */
public class Epsilon implements RegularExpression {
    @Override
    public Automaton buildAutomaton() {
        State start = State.create();
        State end = State.create();

        start.addEpsilonTransition(end);

        return new Automaton(start, end);
    }
}
