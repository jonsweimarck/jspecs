package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.builders.PatternContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepDefinitionParserForStringList implements StepDefinitionParser {

    private String pattern;
    private String placeholder;
    private StepDefinition.StepDefinitionForList stepDefForList;

    private static final String regExpForList = "\\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+";


    public StepDefinitionParserForStringList(String pattern, String placeholder, StepDefinition.StepDefinitionForList<String> stepDef) {
        this.pattern = pattern;
        this.stepDefForList = stepDef;
        this.placeholder = placeholder;
    }

    public ExecutableStepDefinition parse(String matchedString){
        List<String> list = new ArrayList();

        Matcher matcher = Pattern.compile(regExpForList).matcher(matchedString);
        matcher.find();
        String onlyMDList = matcher.group();
        String[] arr = onlyMDList.split("\\*");
        for(String s: arr){
            if(s.trim().length() > 0){
                list.add(s.trim());
            }
        }

        return new ExecutableStepDefinitionForList<String>(stepDefForList, list);
    }


    @Override
    public PatternContainer getStringPattern() {
        String replaced = pattern.replace(placeholder, regExpForList);
        return new PatternContainer(pattern, replaced);
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public StepDefinitionParser cloneWith(String newPattern) {
        throw new NoSuchMethodError();
    }
}
