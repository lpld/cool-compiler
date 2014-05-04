package com.github.lpld.cool.lexing.automata.dfa;

import com.github.lpld.cool.lexing.automata.nfa.Automaton;
import com.github.lpld.cool.lexing.automata.nfa.State;
import com.github.lpld.cool.lexing.automata.nfa.Transition;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * @author leopold
 * @since 5/2/14
 */
public class DfaBuilder {
    private final State startState;
    private final State endState;

    private TransitionsTable transitionsTable = new TransitionsTable();
    private Map<CompoundState, CompoundState> alreadyProcessed = new HashMap<>();

    public DfaBuilder(Automaton nfa) {
        this.startState = nfa.startState();
        this.endState = nfa.endState();
    }

    public TransitionsTable build() {
        CompoundState start = build(startState.epsilonClosure());

        transitionsTable.setStartState(start);

        return transitionsTable;
    }

    private CompoundState build(Set<State> states) {
        CompoundState state = new CompoundState(Sets.newHashSet(
                Iterables.transform(states, new Function<State, Long>() {
                    @Override
                    public Long apply(State input) {
                        return input.getId();
                    }
                })
        ), states.contains(endState));

        if (alreadyProcessed.containsKey(state)) {
            return alreadyProcessed.get(state);
        } else {
            alreadyProcessed.put(state, state);
        }

        //getting all transitions inside of the set of states (grouped by transition symbol)
        Map<String, Collection<Transition>> internal = internalTransitions(states);

        // iterate over all transitions and calculate the new compound states that
        // a symbol transits to
        for (String symbol : internal.keySet()) {
            Collection<Transition> transitions = internal.get(symbol);

            // a new set of states
            Set<State> compoundTransitionTo = new HashSet<>();

            for (Transition transition : transitions) {
                compoundTransitionTo.addAll(transition.getTransitionTo().epsilonClosure());
            }

            CompoundState compound = build(compoundTransitionTo);

            transitionsTable.putTransition(state, symbol, compound);
        }

        return state;
    }

    private Map<String, Collection<Transition>> internalTransitions(Set<State> states) {

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

}
