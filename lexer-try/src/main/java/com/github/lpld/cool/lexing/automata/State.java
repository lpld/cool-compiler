package com.github.lpld.cool.lexing.automata;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author leopold
 * @since 5/1/14
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class State {

    private final long id;

    private static long lastId = 0;

    private Set<State> epsilonTransitions = new HashSet<>();
    private Map<String, State> transitions = new HashMap<>();

    public void addEpsilonTransition(State state) {
        epsilonTransitions.add(state);
    }

    public void addTransition(String symbol, State state) {
        if (transitions.containsKey(symbol)) {
            throw new IllegalArgumentException("The state already contains transition for symbol " + symbol);
        }
        transitions.put(symbol, state);
    }

    /**
     * Not thread safe
     */
    public static State create() {
        return new State(lastId++);
    }

    /**
     * Only id matters
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (id != state.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
