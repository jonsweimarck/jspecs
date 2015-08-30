package se.bitbybit.jspecs;

import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParser;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface KeyExampleExecuter {
    void execute(Map<Pattern, List<StepDefinitionRegexpParser>> searchString2stepDefParser);
}
