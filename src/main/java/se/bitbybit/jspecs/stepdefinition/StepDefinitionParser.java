package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.builders.PatternContainer;

import java.util.List;

public interface StepDefinitionParser {
//    ExecutableStepDefinition parse(String matchedString);

    PatternContainer getStringPattern();
    String getPlaceholder();
    StepDefinitionParser cloneWith(String pattern);

    List<ComparableExecutableStepDefinition> createExecutablesFrom(String keyExampleText);
}
