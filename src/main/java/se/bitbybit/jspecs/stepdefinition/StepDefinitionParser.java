package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.builders.PatternContainer;

public interface StepDefinitionParser {
    ExecutableStepDefinition parse(String matchedString);

    PatternContainer getStringPattern();
    String getPlaceholder();
    StepDefinitionParser cloneWith(String pattern);
}
