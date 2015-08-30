package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.Patterns;

import java.util.regex.Pattern;

public class StepDefinitionRegexpParserForList implements StepDefinitionRegexpParser {

    private String pattern;
    private String placeholder;
    private StepDefinition.StepDefinitionForList stepDefForList;


    public StepDefinitionRegexpParserForList(String pattern, StepDefinition.StepDefinitionForList stepDef) {
        this.pattern = pattern;
        this.stepDefForList = stepDef;
    }

    public StepDefinitionRegexpParserForList(String pattern, String placeholder, StepDefinition.StepDefinitionForList<String> stepDef) {
        this(pattern, stepDef);
        this.placeholder = placeholder;
    }

    public ExecutableStepDefinition parse(String matchedString){
        throw new NoSuchMethodError();
    }

    public Pattern patternAsRegexp() {
       return Pattern.compile(pattern.replace(Patterns.LIST_STRING, "\\* ([A-Z])\\w+"));
    }
}
