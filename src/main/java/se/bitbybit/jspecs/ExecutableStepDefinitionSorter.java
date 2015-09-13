package se.bitbybit.jspecs;


import se.bitbybit.jspecs.stepdefinition.OrderedExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.ParserCombiner;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecutableStepDefinitionSorter {

    public List<OrderedExecutableStepDefinition> sortOnMatches(Collection<ParserCombiner> parserCombiners, String keyExampleText){
        List<OrderedExecutableStepDefinition> result = new ArrayList<>();
        List<StepDefinitionParser> parsers = flattenParserLists(parserCombiners);
        for(StepDefinitionParser parser : parsers){
            Matcher matcher = Pattern.compile(parser.getStringPattern().getRegexpPattern()).matcher(keyExampleText);
            while(matcher.find()){
                result.add(new OrderedExecutableStepDefinition(matcher.start(), parser.parse(matcher.group())));
            }
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
