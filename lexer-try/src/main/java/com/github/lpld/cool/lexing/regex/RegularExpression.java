package com.github.lpld.cool.lexing.regex;

import com.github.lpld.cool.lexing.automata.Automaton;

/**
 * @author leopold
 * @since 5/1/14
 */
public interface RegularExpression {
    Automaton buildAutomaton();
}
