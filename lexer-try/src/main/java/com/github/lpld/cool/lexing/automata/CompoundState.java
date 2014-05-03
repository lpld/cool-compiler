package com.github.lpld.cool.lexing.automata;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimaps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * @author leopold
 * @since 5/2/14
 */
@RequiredArgsConstructor
public class CompoundState {
    @Getter
    private final Set<State> states;

    @Getter
    private final Set<Transition<CompoundState>> transitions = new HashSet<>();

    public Map<String, Collection<Transition<State>>> internalTransitions() {

        Iterable<State> withTransitions = Iterables.filter(states, new Predicate<State>() {
            @Override
            public boolean apply(State input) {
                return input.getTransition() != null;
            }
        });

        Iterable<Transition<State>> internal = Iterables.transform(withTransitions, new Function<State, Transition<State>>() {
            @Override
            public Transition<State> apply(State input) {
                return input.getTransition();
            }
        });

        return Multimaps.index(internal, new Function<Transition<State>, String>() {
            @Override
            public String apply(Transition<State> input) {
                return input.getSymbol();
            }
        }).asMap();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundState that = (CompoundState) o;

        if (states != null ? !states.equals(that.states) : that.states != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return states != null ? states.hashCode() : 0;
    }
}
