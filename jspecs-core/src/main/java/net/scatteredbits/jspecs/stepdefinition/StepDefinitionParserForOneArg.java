package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.OneArgMethod;
import net.scatteredbits.jspecs.builders.PatternContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType;

public class StepDefinitionParserForOneArg<T> implements StepDefinitionParser, Cloneable {

    private String pattern;
    private String placeholder;
    private PlaceHolderType argumentType;
    private String groupName = "a" + UUID.randomUUID().toString().substring(32);
    private OneArgMethod<T> stepDefinition;
    private TypeExtractor<T> typeExtractor;

    public StepDefinitionParserForOneArg(String pattern,
                                         String placeholder,
                                         PlaceHolderType argumentType,
                                         OneArgMethod<T> stepDef) {
        this.pattern = pattern;
        this.stepDefinition = stepDef;
        this.placeholder = placeholder;
        this.argumentType = argumentType;
        this.typeExtractor = new TypeExtractor<T>();
    }

    @Override
    public StepDefinitionParser cloneWith(String newPattern) {
        return new StepDefinitionParserForOneArg<T>(newPattern, this.placeholder, this.argumentType, this.stepDefinition);
    }

    @Override
    public PatternContainer getStringPattern() {
        return new PatternContainer(argumentType, placeholder, pattern, groupName);
    }

    @Override
    public String getPlaceholderT() {
        return placeholder;
    }

    @Override
    public String getPlaceholderU() {
        return null;
    }


    @Override
    public List<ComparableExecutableStepDefinition> createExecutablesFrom(String keyExampleText) {
        List<ComparableExecutableStepDefinition> result = new ArrayList<>();

        Matcher matcher = Pattern.compile(getStringPattern().getRegexpPatternWithGroupName(), Pattern.UNICODE_CHARACTER_CLASS).matcher(keyExampleText);
        int ç = 0;
        while(matcher.find()){
            T arg = typeExtractor.extractArg(argumentType, matcher.group(groupName));
            ExecutableStepDefinitionForOneArg esdo = new ExecutableStepDefinitionForOneArg<T, Object>(stepDefinition, arg);
            result.add(new ComparableExecutableStepDefinition(matcher.start(groupName), esdo));
        }

        return result;
    }

}
