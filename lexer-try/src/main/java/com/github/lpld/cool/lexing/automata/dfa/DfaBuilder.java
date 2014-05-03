package com.github.lpld.cool.lexing.automata.dfa;

import com.github.lpld.cool.lexing.automata.nfa.Automaton;
import com.github.lpld.cool.lexing.automata.nfa.State;
import com.github.lpld.cool.lexing.automata.nfa.Transition;
import lombok.RequiredArgsConstructor;

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
        CompoundState start = new CompoundState(startState.epsilonClosure());

        build(start);

        return transitionsTable;
    }

    private void build(CompoundState state) {

        //getting all transitions inside of the set of states (grouped by transition symbol)
        Map<String, Collection<Transition>> internal = state.internalTransitions();

        // iterate over all transitions and calculate the new compound states that
        // a symbol transits to
        for (String symbol : internal.keySet()) {
            Collection<Transition> transitions = internal.get(symbol);

            // a new set of states
            Set<State> compoundTransitionTo = new HashSet<>();

            for (Transition transition : transitions) {
                compoundTransitionTo.addAll(transition.getTransitionTo().epsilonClosure());
            }

            CompoundState compound = new CompoundState(compoundTransitionTo);

            // intern compound state
            if (alreadyProcessed.containsKey(compound)) {
                compound = alreadyProcessed.get(compound);
            } else {
                alreadyProcessed.put(compound, compound);
                build(compound);
            }

            transitionsTable.putTransition(state, symbol, compound);
        }
    }

}
