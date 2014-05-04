package com.github.lpld.cool.lexing.automata.dfa;

import lombok.Data;

import java.util.Set;

/**
 * @author leopold
 * @since 5/2/14
 */
@Data
public class CompoundState {
    private final Set<Long> stateIds;
    private final boolean accepts;
}
