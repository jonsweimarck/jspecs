package se.bitbybit.jspecs;


import se.bitbybit.jspecs.stepdefinition.ComparableExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.ParserCombiner;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExecutableStepDefinitionSorter {

    public List<ComparableExecutableStepDefinition> sortOnMatches(Collection<ParserCombiner> parserCombiners, String keyExampleText){
        List<ComparableExecutableStepDefinition> result = new ArrayList<>();
        List<StepDefinitionParser> parsers = flattenParserLists(parserCombiners);

        for(StepDefinitionParser parser : parsers){
            result.addAll(parser.createExecutablesFrom(keyExampleText));
        }

        Collections.sort(result);
        return result;
    }

    private List<StepDefinitionParser> flattenParserLists(Collection<ParserCombiner> parserCombiners) {
        List<StepDefinitionParser> result = new ArrayList<>();
        for(ParserCombiner pc : parserCombiners){
            result.addAll(pc.getParsers());
        }
        return result;
    }

}
