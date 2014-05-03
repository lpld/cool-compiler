package com.github.lpld.cool.lexing.automata;

import lombok.AccessLevel;
import lombok.Getter;
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

    // in this model a state has only one transition
    @Getter
    private Transition<State> transition;

    public void addEpsilonTransition(State state) {
        epsilonTransitions.add(state);
    }

    /**
     * Not thread safe
     */
    public static State create() {
        return new State(lastId++);
    }

    public static State createWithTransition(String symbol, State transitionTo) {
        State state = new State(lastId++);
        state.transition = new Transition<>(symbol, transitionTo);
        return state;
    }

    public Set<State> epsilonClosure() {
        Set<State> epsilonClosure = new HashSet<>();
        epsilonClosure.add(this);
        fillEpsilonClosureSet(epsilonClosure);
        return epsilonClosure;
    }

    private void fillEpsilonClosureSet(Set<State> epsilonClosure) {
        for (State epsilonTransition : epsilonTransitions) {
            if (!epsilonClosure.contains(epsilonTransition)) {
                epsilonClosure.add(epsilonTransition);
                epsilonTransition.fillEpsilonClosureSet(epsilonClosure);
            }
        }
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
