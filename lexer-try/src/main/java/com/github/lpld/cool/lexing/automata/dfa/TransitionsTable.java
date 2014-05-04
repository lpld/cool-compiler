package com.github.lpld.cool.lexing.automata.dfa;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leopold
 * @since 5/3/14
 */
public class TransitionsTable {
    @Setter
    private CompoundState startState;

    private Map<CompoundState, Map<String, CompoundState>> transitions = new HashMap<>();


    public void putTransition(CompoundState from, String symbol, CompoundState to) {
        Map<String, CompoundState> fromTransitions = getOrCreateTransitions(from);

        fromTransitions.put(symbol, to);

        // just put empty record to transitions map in case if it's absent there
        getOrCreateTransitions(to);
    }

    private Map<String, CompoundState> getOrCreateTransitions(CompoundState key) {
        Map<String, CompoundState> transitionsForKey = transitions.get(key);

        if (transitionsForKey == null) {
            transitionsForKey = new HashMap<>();
            transitions.put(key, transitionsForKey);
        }

        return transitionsForKey;
    }
}
