package se.bbb.jspecs;

import java.util.regex.Pattern;

public interface StepDefinitionRegexpParser {
    ExecutableStepDefinition parse(String matchedString);

    Pattern patternAsRegexp();
}
