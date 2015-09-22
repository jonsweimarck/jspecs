package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.PatternContainer;
import net.scatteredbits.jspecs.builders.SearchPatterns;
import net.scatteredbits.jspecs.builders.TwoArgMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepDefinitionParserForTwoArg<T, U> implements StepDefinitionParser, Cloneable {

    private String pattern;
    private String placeholderT;
    private String placeholderU;
    private SearchPatterns.PlaceHolderType argumentTypeT;
    private SearchPatterns.PlaceHolderType argumentTypeU;
    private String groupNameT = "a" + UUID.randomUUID().toString().substring(32);
    private String groupNameU = "a" + UUID.randomUUID().toString().substring(32);
    private TwoArgMethod<T, U> stepDefinition;
    private TypeExtractor<T> typeExtractorT;
    private TypeExtractor<U> typeExtractorU;

    public StepDefinitionParserForTwoArg(String pattern,
                                         String placeholderT,
                                         String placeholderU,
                                         SearchPatterns.PlaceHolderType argumentTypeT,
                                         SearchPatterns.PlaceHolderType argumentTypeU,
                                         TwoArgMethod<T, U> stepDef) {
        this.pattern = pattern;
        this.stepDefinition = stepDef;
        this.placeholderT = placeholderT;
        this.placeholderU = placeholderU;
        this.argumentTypeT = argumentTypeT;
        this.argumentTypeU = argumentTypeU;
        this.typeExtractorT = new TypeExtractor<T>();
        this.typeExtractorU = new TypeExtractor<U>();
    }

    @Override
    public StepDefinitionParser cloneWith(String newPattern) {
        return new StepDefinitionParserForTwoArg(newPattern, this.placeholderT, this.placeholderU, this.argumentTypeT, this.argumentTypeU,this.stepDefinition);
    }

    @Override
    public PatternContainer getStringPattern() {
//        String replacedWithGroupName = pattern.
//                replace(placeholderT, "(?<"+ groupNameT +">\\w+)").
//                replace(placeholderU, "(?<"+ groupNameU +">\\w+)");
//        String replacedWithoutGroupName = pattern.
//                replace(placeholderT, "(\\w+)").
//                replace(placeholderU, "(\\w+)");

        return new PatternContainer(pattern, argumentTypeT, argumentTypeU, placeholderT, placeholderU, groupNameT, groupNameU);

//        return new PatternContainer(pattern, replacedWithGroupName, replacedWithoutGroupName);
    }

    @Override
    public String getPlaceholderT() {
        return placeholderT;
    }

    @Override
    public String getPlaceholderU() {
        return placeholderT;
    }


    @Override
    public List<ComparableExecutableStepDefinition> createExecutablesFrom(String keyExampleText) {
        List<ComparableExecutableStepDefinition> result = new ArrayList<>();

        Matcher matcher = Pattern.compile(getStringPattern().getRegexpPatternWithGroupName(), Pattern.UNICODE_CHARACTER_CLASS).matcher(keyExampleText);
        while(matcher.find()){
            T t = typeExtractorT.extractArg(argumentTypeT, matcher.group(groupNameT));
            U u = typeExtractorU.extractArg(argumentTypeU, matcher.group(groupNameU));
            ExecutableStepDefinitionForTwoArg esdo = new ExecutableStepDefinitionForTwoArg<T, U>(stepDefinition, t, u);
            result.add(new ComparableExecutableStepDefinition(matcher.start(), esdo));
        }

        return result;
    }

}
