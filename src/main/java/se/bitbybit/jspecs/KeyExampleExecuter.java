package se.bitbybit.jspecs;

import se.bitbybit.jspecs.stepdefinition.ParserCombiner;

import java.util.Collection;

public interface KeyExampleExecuter {
    void execute(String keyExampleText, Collection<ParserCombiner> parserCombiners);
}
