package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.builders.PatternContainer;

import static se.bitbybit.jspecs.stepdefinition.StepDefinition.StepDefinitionForObject;

public class StepDefinitionParserForString implements StepDefinitionParser, Cloneable {

    private String pattern;
    private String placeholder;
    private StepDefinitionForObject stepDefForObject;

    public StepDefinitionParserForString(String pattern, String placeholder, StepDefinitionForObject stepDef) {
        this.pattern = pattern;
        this.stepDefForObject = stepDef;
        this.placeholder = placeholder;
    }


    public ExecutableStepDefinition parse(String matchedString){
        int start = pattern.indexOf(placeholder);
        int stop = start + placeholder.length();

        String prefixToRemove = pattern.substring(0, start);
        String suffixToRemove = pattern.substring(stop);

        String noPrefix = matchedString.replaceFirst(prefixToRemove, "");
        String noPreSuf = noPrefix.replaceFirst(suffixToRemove, "");

        return new ExecutableStepDefinitionForObject(stepDefForObject, noPreSuf);
    }

    @Override
    public PatternContainer getStringPattern() {
        String replaced = pattern.replace(placeholder, "(.*)");
        return new PatternContainer(pattern, replaced);
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public StepDefinitionParser cloneWith(String newPattern) {
        return new StepDefinitionParserForString(newPattern, this.placeholder, this.stepDefForObject);
    }

}
