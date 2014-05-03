package com.github.lpld.cool.lexing.automata.dfa;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leopold
 * @since 5/3/14
 */
public class TransitionsTable {
    private Map<CompoundState, Map<String, CompoundState>> transitions = new HashMap<>();

    public void putTransition(CompoundState from, String symbol, CompoundState to) {
        Map<String, CompoundState> fromTransitions = transitions.get(from);

        if (fromTransitions == null) {
            fromTransitions = new HashMap<>();
            transitions.put(from, fromTransitions);
        }

        fromTransitions.put(symbol, to);
    }


}
