package se.bitbybit.jspecs.builders;

import se.bitbybit.jspecs.stepdefinition.OrderedExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserMap {

    Map<String, List<StepDefinitionParser>> map = new HashMap();

    public ParserMap(List<StepDefinitionParser> parsers) {
            for(StepDefinitionParser parser: parsers){
                add(parser);
            }
    }

    private void add(StepDefinitionParser parser) {
        String pattern = parser.getStringPattern().getOriginalPattern();

        List<StepDefinitionParser> existingParsers = map.get(pattern);
        if(existingParsers == null){
            existingParsers = new ArrayList<>();
        }
        existingParsers.add(parser);
        map.put(pattern, existingParsers);
    }


    public List<OrderedExecutableStepDefinition> getExecutableStepDefsFor(String keyExampleText) {
//        combineStepDefinitions();

        return createOrderedExecutableStepDefs(keyExampleText);

    }

    private List<OrderedExecutableStepDefinition> createOrderedExecutableStepDefs(String keyExampleText) {

        List<OrderedExecutableStepDefinition> orderedExecutableStepDefs = new ArrayList<>();

        for (List<StepDefinitionParser> list : map.values()) {
            for(StepDefinitionParser entry: list) {

                Pattern regExp = Pattern.compile(entry.getStringPattern().getRegexpPattern());
                Matcher matcher = regExp.matcher(keyExampleText);
                // check all occurance
                while (matcher.find()) {
                    System.out.print("Start index: " + matcher.start());
                    System.out.print(" End index: " + matcher.end() + " ");
                    System.out.println(matcher.group());

                    orderedExecutableStepDefs.add(new OrderedExecutableStepDefinition(
                            matcher.start(),
                            entry.parse(matcher.group())));
                }
            }
            Collections.sort(orderedExecutableStepDefs);
        }
        return orderedExecutableStepDefs;
    }

//    private void combineStepDefinitions() {
//        for(Map.Entry<String, List<StepDefinitionParser>> entry : this.map.entrySet()){
//            if(entry.getValue().size() > 0){
//                map.put(entry.getKey(), combineParsersForSamePattern(entry.getValue()));
//            }
//        }
//    }
//
//    private List<StepDefinitionParser> combineParsersForSamePattern(List<StepDefinitionParser> parsers) {
//        List<StepDefinitionParser> result = new ArrayList<>();
//
//        List<String> allPlaceholders = getPlaceholders(parsers);
//
//        for(StepDefinitionParser parser : parsers){
//            String parserPlaceholder = parser.getPlaceholder();
//            String parsersPattern = parser.getStringPattern().getOriginalPattern();
//            for(String placeholder: allPlaceholders){
//                if(! placeholder.equals(parserPlaceholder)){
//
//                }
//            }
//        }
//    }
}
