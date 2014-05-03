package com.github.lpld.cool.lexing.automata;

import lombok.Data;

/**
 * No generic needed here probably. Better to have two separate Transition objects for
 * State and CompoundState.
 * <p/>
 * @author leopold
 * @since 5/3/14
 */
@Data
public class Transition<T> {
    private final String symbol;
    private final T transitionTo;

}
