package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.Patterns;

import java.util.regex.Pattern;

import static se.bitbybit.jspecs.Patterns.STRING;
import static se.bitbybit.jspecs.stepdefinition.StepDefinition.StepDefinitionForObject;

public class StepDefinitionRegexpParserForObject implements StepDefinitionRegexpParser {

    private String pattern;
    private String placeholder;
    private StepDefinitionForObject stepDefForObject;

    public StepDefinitionRegexpParserForObject( String pattern, StepDefinitionForObject stepDef) {
        this.pattern = pattern;
        this.stepDefForObject = stepDef;
    }

    public StepDefinitionRegexpParserForObject(String pattern, String placeholder, StepDefinitionForObject<String> stepDef) {
        this(pattern, stepDef);
        this.placeholder = placeholder;
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
        if(placeholder == null) {
            String replaced = pattern.replace(Patterns.STRING, "(.*)");
            return Pattern.compile(replaced);
        }
        String replaced = pattern.replace(placeholder, "(.*)");
        return Pattern.compile(replaced);
    }
}
