package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;

import java.util.Collection;

public interface ISearchPattern {

    Collection<ParserCombiner> getParserCombiners();
}
