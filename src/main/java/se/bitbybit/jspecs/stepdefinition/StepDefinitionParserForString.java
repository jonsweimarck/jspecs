package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.builders.PatternContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static se.bitbybit.jspecs.stepdefinition.StepDefinition.StepDefinitionForObject;

public class StepDefinitionParserForString implements StepDefinitionParser, Cloneable {

    private String pattern;
    private String placeholder;
    private String groupName = "a" + UUID.randomUUID().toString().substring(32);
    private StepDefinitionForObject stepDefForObject;

    public StepDefinitionParserForString(String pattern, String placeholder, StepDefinitionForObject stepDef) {
        this.pattern = pattern;
        this.stepDefForObject = stepDef;
        this.placeholder = placeholder;
    }

    @Override
    public PatternContainer getStringPattern() {
        String replacedWithGroupName = pattern.replace(placeholder, "(?<"+ groupName +">\\w+)");
        String replacedWithoutGroupName = pattern.replace(placeholder, "(\\w+)");
        return new PatternContainer(pattern, replacedWithGroupName, replacedWithoutGroupName);
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public StepDefinitionParser cloneWith(String newPattern) {
        return new StepDefinitionParserForString(newPattern, this.placeholder, this.stepDefForObject);
    }

    @Override
    public List<ComparableExecutableStepDefinition> createExecutablesFrom(String keyExampleText) {
        List<ComparableExecutableStepDefinition> result = new ArrayList<>();

        Matcher matcher = Pattern.compile(getStringPattern().getRegexpPatternWithGroupName(), Pattern.UNICODE_CHARACTER_CLASS).matcher(keyExampleText);
        while(matcher.find()){
            ExecutableStepDefinitionForObject esdo = new ExecutableStepDefinitionForObject(stepDefForObject, matcher.group(groupName));
            result.add(new ComparableExecutableStepDefinition(matcher.start(), esdo));
        }

        return result;
    }

}
