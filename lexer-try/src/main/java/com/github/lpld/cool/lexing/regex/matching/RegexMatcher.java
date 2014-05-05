package com.github.lpld.cool.lexing.regex.matching;

import com.github.lpld.cool.lexing.automata.dfa.CompoundState;
import com.github.lpld.cool.lexing.automata.dfa.DfaBuilder;
import com.github.lpld.cool.lexing.automata.dfa.TransitionsTable;
import com.github.lpld.cool.lexing.automata.nfa.Automaton;
import com.github.lpld.cool.lexing.regex.RegularExpression;
import com.github.lpld.cool.lexing.regex.parsing.RegexParser;

import java.util.Collection;
import java.util.Map;

/**
 * @author leopold
 * @since 5/5/14
 */
public class RegexMatcher {
    private TransitionsTable dfaTable;

    public RegexMatcher(String regexString) {
        RegularExpression regex = new RegexParser(regexString).parse();
        Automaton automaton = regex.buildAutomaton();
        dfaTable = new DfaBuilder(automaton).build();
    }

    public boolean matches(String input) {
        CompoundState currentState = dfaTable.getStartState();
        for (char character : input.toCharArray()) {
            Map<String, CompoundState> transitions = dfaTable.getTransitions().get(currentState);

            if (transitions == null || !transitions.containsKey(String.valueOf(character))) {
                return false;
            }

            currentState = transitions.get(String.valueOf(character));
        }

        return currentState.isAccepts();
    }
}
