package com.github.lpld.cool.lexing.regex.matching;

import com.github.lpld.cool.lexing.automata.dfa.CompoundState;
import com.github.lpld.cool.lexing.automata.dfa.DfaBuilder;
import com.github.lpld.cool.lexing.automata.dfa.TransitionsTable;
import com.github.lpld.cool.lexing.automata.nfa.Automaton;
import com.github.lpld.cool.lexing.regex.RegularExpression;
import com.github.lpld.cool.lexing.chars.CharBuffer;
import com.github.lpld.cool.lexing.regex.parsing.RegexParser;

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

    public boolean match(String input) {
        return match(new CharBuffer(input)) == input.length() - 1;
    }

    public int match(CharBuffer input) {
        CompoundState currentState = dfaTable.getStartState();
        //CompoundState acceptState = null;
        int matchedIndex = -1;

        for (int i = 0; i < input.length(); i++) {

            char character = input.charAt(i);
            Map<String, CompoundState> transitions = dfaTable.getTransitions().get(currentState);

            if (transitions == null || !transitions.containsKey(String.valueOf(character))) {
                break;
            }

            currentState = transitions.get(String.valueOf(character));
            if (currentState.isAcceptState()) {
                //acceptState = currentState;
                matchedIndex = i;
            }
        }


        return matchedIndex;
    }


}
