package se.bbb.jspecs;

import java.util.regex.Pattern;

import static se.bbb.jspecs.Patterns.STRING;
import static se.bbb.jspecs.StepDefinition.StepDefinitionForObject;

public class StepDefinitionRegexpParserForObject implements StepDefinitionRegexpParser {

    private String pattern;
    private StepDefinitionForObject stepDefForObject;

    public StepDefinitionRegexpParserForObject( String pattern, StepDefinitionForObject stepDef) {
        this.pattern = pattern;
        this.stepDefForObject = stepDef;
    }


    public ExecutableStepDefinition parse(String matchedString){
        int start = pattern.indexOf(STRING);
        int stop = start + STRING.length();

        String prefixToRemove = pattern.substring(0, start);
        String suffixToRemove = pattern.substring(stop);

        String noPrefix = matchedString.replaceFirst(prefixToRemove, "");
        String noPreSuf = noPrefix.replaceFirst(suffixToRemove, "");


        return new ExecutableStepDefinitionForObject<String>(stepDefForObject, noPreSuf);
    }

    public Pattern patternAsRegexp() {
        throw new NoSuchMethodError();
    }
}
