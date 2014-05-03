package com.github.lpld.cool.lexing.automata.dfa;

import com.github.lpld.cool.lexing.automata.nfa.State;
import com.github.lpld.cool.lexing.automata.nfa.Transition;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimaps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * todo: add final state
 *
 * @author leopold
 * @since 5/2/14
 */
@RequiredArgsConstructor
public class CompoundState {
    @Getter
    private final Set<State> states;

    public Map<String, Collection<Transition>> internalTransitions() {

        Iterable<State> withTransitions = Iterables.filter(states, new Predicate<State>() {
            @Override
            public boolean apply(State input) {
                return input.getTransition() != null;
            }
        });

        Iterable<Transition> internal = Iterables.transform(withTransitions, new Function<State, Transition>() {
            @Override
            public Transition apply(State input) {
                return input.getTransition();
            }
        });

        return Multimaps.index(internal, new Function<Transition, String>() {
            @Override
            public String apply(Transition input) {
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
