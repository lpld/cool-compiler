package com.github.lpld.cool.lexing.automata;

import java.util.*;

/**
 * @author leopold
 * @since 5/2/14
 */
public class DfaBuilder {
    // change this, don't use set as key in map
    private Map<Set<State>, CompoundState> dfaStates = new HashMap<>();

    public CompoundState build(Automaton automaton) {
        State startState = automaton.startState();
        State endState = automaton.endState();

        CompoundState start = new CompoundState(startState.epsilonClosure());

        build(start);

        return start;
    }

    private void build(CompoundState state) {
//        if (dfaStates.containsKey(states)) {
//            return;
//        }
//
//        CompoundState state = new CompoundState(states);
//        dfaStates.put(states, state);

        //getting all transitions inside of the set of states (grouped by transition symbol)
        Map<String, Collection<Transition<State>>> internal = state.internalTransitions();

        // iterate over all transitions and calculate the new compound states that
        // a symbol transits to
        for (String symbol : internal.keySet()) {
            Collection<Transition<State>> transitions = internal.get(symbol);

            // a new set of states
            Set<State> compoundTransitionTo = new HashSet<>();

            for (Transition<State> transition : transitions) {
                compoundTransitionTo.addAll(transition.getTransitionTo().epsilonClosure());
            }

            CompoundState compound;
            if (dfaStates.containsKey(compoundTransitionTo)) {
                compound = dfaStates.get(compoundTransitionTo);
                state.getTransitions().add(new Transition<>(symbol, compound));
            } else {
                compound = new CompoundState(compoundTransitionTo);
                dfaStates.put(compoundTransitionTo, compound);
                state.getTransitions().add(new Transition<>(symbol, compound));
                build(compound);
            }
        }
    }
}
