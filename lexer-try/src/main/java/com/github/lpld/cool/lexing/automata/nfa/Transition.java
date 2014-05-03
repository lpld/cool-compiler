package com.github.lpld.cool.lexing.automata.nfa;

import lombok.Data;

/**
 * @author leopold
 * @since 5/3/14
 */
@Data
public class Transition {
    private final String symbol;
    private final State transitionTo;
}
