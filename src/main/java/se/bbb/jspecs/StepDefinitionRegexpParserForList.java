package se.bbb.jspecs;

import java.util.regex.Pattern;

public class StepDefinitionRegexpParserForList implements StepDefinitionRegexpParser {

    private String pattern;
    private StepDefinition.StepDefinitionForList stepDefForList;


    public StepDefinitionRegexpParserForList(String pattern, StepDefinition.StepDefinitionForList stepDef) {
        this.pattern = pattern;
        this.stepDefForList = stepDef;
    }

    public ExecutableStepDefinition parse(String matchedString){
        throw new NoSuchMethodError();
    }

    public Pattern patternAsRegexp() {
        throw new NoSuchMethodError();
    }
}
