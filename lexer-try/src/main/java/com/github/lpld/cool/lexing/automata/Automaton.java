package com.github.lpld.cool.lexing.automata;

import lombok.RequiredArgsConstructor;

/**
 * todo make this immutable
 *
 * @author leopold
 * @since 5/1/14
 */
@RequiredArgsConstructor
public class Automaton {
    private final State startState;
    private final State endState;

    public State startState() {
        return startState;
    }

    public State endState() {
        return endState;
    }

    public Automaton concatenate(Automaton automaton) {
        this.endState().addEpsilonTransition(automaton.startState());

        return new Automaton(this.startState(), automaton.endState());
    }

    public Automaton union(Automaton automaton) {
        State start = State.create();
        State end = State.create();

        start.addEpsilonTransition(this.startState());
        start.addEpsilonTransition(automaton.startState());

        this.endState().addEpsilonTransition(end);
        automaton.endState().addEpsilonTransition(end);

        return new Automaton(start, end);
    }

    public Automaton iterate() {
        State start = State.create();
        State end = State.create();

        start.addEpsilonTransition(end);
        start.addEpsilonTransition(this.startState());

        this.endState().addEpsilonTransition(start);
        this.endState().addEpsilonTransition(end);

        return new Automaton(start, end);
    }
}
