package net.scatteredbits.jspecs;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;

import java.util.Collection;

public interface KeyExampleExecuter {
    void execute(String keyExampleText, Collection<ParserCombiner> parserCombiners);
}
