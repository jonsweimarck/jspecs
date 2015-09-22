package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.PatternContainer;

import java.util.List;

public interface StepDefinitionParser {
//    ExecutableStepDefinition parse(String matchedString);

    PatternContainer getStringPattern();
    String getPlaceholderT();
    String getPlaceholderU();
    StepDefinitionParser cloneWith(String pattern);

    List<ComparableExecutableStepDefinition> createExecutablesFrom(String keyExampleText);
}
