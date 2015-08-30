package se.bitbybit.jspecs.stepdefinition;

import java.util.regex.Pattern;

public interface StepDefinitionRegexpParser {
    ExecutableStepDefinition parse(String matchedString);

    Pattern patternAsRegexp();
}
