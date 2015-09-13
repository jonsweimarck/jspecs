package se.bitbybit.jspecs.builders;

import se.bitbybit.jspecs.stepdefinition.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SearchPatterns {

    private Map<PatternContainer, ParserCombiner> map = new HashMap<>();
    private String pattern;

    public SearchPatterns() {
    }

    public FunctionBuilder binding(String placeholder) {
        return new FunctionBuilder(placeholder);
    }

    public SearchPatterns addSearchPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    protected Collection<ParserCombiner> getParserCombiners(){
        return map.values();
    }

    private void addToMap(String placeholder, StepDefinition.StepDefinitionForObject<String> stepDef) {
        StepDefinitionParserForString stepDefParser = new StepDefinitionParserForString(pattern, placeholder, stepDef);
        addToMap(stepDefParser);
    }

    private void addToMap(String placeholder, StepDefinition.StepDefinitionForList<String> stepDef) {
        StepDefinitionParserForStringList stepDefParser = new StepDefinitionParserForStringList(pattern, placeholder, stepDef);
        addToMap(stepDefParser);
    }

    private void addToMap(StepDefinitionParser parser) {
        PatternContainer patternContainer = parser.getStringPattern();
        ParserCombiner pc = this.map.get(patternContainer);
        if(pc == null){
            pc = new ParserCombiner();
        }
        this.map.put(patternContainer, pc.add(parser));
    }

    public class FunctionBuilder {

        private String placeholder;

        private FunctionBuilder(String placeholder) {
            this.placeholder = placeholder;
        }

        public SearchPatterns toStringIn(StepDefinition.StepDefinitionForObject<String> stepDef){
            SearchPatterns.this.addToMap(placeholder, stepDef);
            return SearchPatterns.this;
        }

        public SearchPatterns toStringListIn(StepDefinition.StepDefinitionForList<String> stepDef){
            SearchPatterns.this.addToMap(placeholder, stepDef);
            return SearchPatterns.this;
        }

    }
}

