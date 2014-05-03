package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.nfa.Automaton;
import com.github.lpld.cool.lexing.automata.nfa.State;

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
