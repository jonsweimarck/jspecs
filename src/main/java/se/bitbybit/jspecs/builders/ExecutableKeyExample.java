package se.bitbybit.jspecs.builders;

import se.bitbybit.jspecs.KeyExampleExecuter;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParser;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParserForList;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParserForObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static se.bitbybit.jspecs.stepdefinition.StepDefinition.StepDefinitionForList;
import static se.bitbybit.jspecs.stepdefinition.StepDefinition.StepDefinitionForObject;

public class ExecutableKeyExample {

    private String keyExample;
    private Map<Pattern, List<StepDefinitionRegexpParser>> searchString2stepDefParser = new HashMap<>();


    private ExecutableKeyExample(String keyExample){
        this.keyExample = keyExample;
    }

    public static ExecutableKeyExample forKeyExample(String keyExample){
        return new ExecutableKeyExample(keyExample);
    }


    public SearchPatternBuilder addSearchPattern(String pattern) {

        return new SearchPatternBuilder(pattern);
    }

    public class SearchPatternBuilder {

        private String pattern;

        protected SearchPatternBuilder(String pattern) {
            this.pattern = pattern;
        }

        public FunctionBuilder binding(String placeholder) {
            return new FunctionBuilder(placeholder);
        }

        public SearchPatternBuilder addSearchPattern(String pattern) {
            return new SearchPatternBuilder(pattern);
        }

        private void addToMap(String placeholder, StepDefinitionForObject<String> stepDef) {
            StepDefinitionRegexpParserForObject stepDefParser = new StepDefinitionRegexpParserForObject(pattern, placeholder, stepDef);
            addToMap(stepDefParser);
        }

        private void addToMap(String placeholder, StepDefinitionForList<String> stepDef) {
            StepDefinitionRegexpParserForList stepDefParser = new StepDefinitionRegexpParserForList(pattern, placeholder, stepDef);
            addToMap(stepDefParser);
        }

        private void addToMap(StepDefinitionRegexpParser parser) {
            Pattern patternAsRegexp = parser.patternAsRegexp();

            List<StepDefinitionRegexpParser> existingParsers = ExecutableKeyExample.this.searchString2stepDefParser.get(patternAsRegexp);
            if(existingParsers == null){
                existingParsers = new ArrayList<>();
            }
            existingParsers.add(parser);
            ExecutableKeyExample.this.searchString2stepDefParser.put(parser.patternAsRegexp(), existingParsers);
        }

        public void run(KeyExampleExecuter keyExampleExecuter) {
            keyExampleExecuter.execute(ExecutableKeyExample.this.searchString2stepDefParser);
        }


        public class FunctionBuilder {

            private String placeholder;

            private FunctionBuilder(String placeholder) {
                this.placeholder = placeholder;
            }

            public SearchPatternBuilder toStringIn(StepDefinitionForObject<String> stepDef){
                SearchPatternBuilder.this.addToMap(placeholder, stepDef);
                return SearchPatternBuilder.this;
            }

            public SearchPatternBuilder toStringListIn(StepDefinitionForList<String> stepDef){
                SearchPatternBuilder.this.addToMap(placeholder, stepDef);
                return SearchPatternBuilder.this;
            }

        }

    }

}
