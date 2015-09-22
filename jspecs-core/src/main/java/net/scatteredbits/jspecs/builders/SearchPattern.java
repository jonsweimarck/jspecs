package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;
import net.scatteredbits.jspecs.stepdefinition.StepDefinitionParser;
import net.scatteredbits.jspecs.stepdefinition.StepDefinitionParserForOneArg;
import net.scatteredbits.jspecs.stepdefinition.StepDefinitionParserForTwoArg;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPattern implements ISearchPattern{
    private Map<String, ParserCombiner> map = new HashMap<>();
    private String pattern;


    public SearchPattern(String pattern){
        this.pattern = pattern;
    }

    public BindingOneArg binding(String placeholder, SearchPatterns.PlaceHolderType placeholderType) {

        switch(placeholderType){
            case asString : return new BindingOneArg<String>(placeholder, placeholderType);
            case asInteger : return new BindingOneArg<Integer>(placeholder, placeholderType);
            case asStringList : return new BindingOneArg<List<String>>(placeholder, placeholderType);
            case asIntegerList : return new BindingOneArg<List<Integer>>(placeholder, placeholderType);
        }

        throw new IllegalArgumentException("Not legal placeholdertype: " + placeholderType);
    }

    public Collection<ParserCombiner> getParserCombiners() {
        return map.values();
    }

    private void add(StepDefinitionParser parser) {
        PatternContainer patternContainer = parser.getStringPattern();
        ParserCombiner pc = this.map.get(patternContainer.getOriginalPattern());
        if(pc == null){
            pc = new ParserCombiner();
        }
        this.map.put(patternContainer.getOriginalPattern(), pc.add(parser));
    }


    public class BindingOneArg<T> {
        private String firstArgPlaceholder;
        private SearchPatterns.PlaceHolderType firstArgType;

        public BindingOneArg(String firstArgPlaceholder, SearchPatterns.PlaceHolderType firstArgType){
            this.firstArgPlaceholder = firstArgPlaceholder;
            this.firstArgType = firstArgType;
        }

        public BindingTwoArgs binding(String secondArgPlaceholder, SearchPatterns.PlaceHolderType secondArgType) {
            switch(secondArgType){
                case asString : return new BindingTwoArgs<T, String>(firstArgPlaceholder, secondArgPlaceholder,  firstArgType, secondArgType);
                case asInteger: return new BindingTwoArgs<T, Integer>(firstArgPlaceholder, secondArgPlaceholder,  firstArgType, secondArgType);
                case asStringList: return new BindingTwoArgs<T, List<String>>(firstArgPlaceholder, secondArgPlaceholder,  firstArgType, secondArgType);
                case asIntegerList: return new BindingTwoArgs<T, List<Integer>>(firstArgPlaceholder, secondArgPlaceholder,  firstArgType, secondArgType);
            }
            throw new IllegalArgumentException("Not legal placeholdertype: " + secondArgType);
        }

        public SearchPattern to(OneArgMethod<T> oneArgMethod) {
            add(new StepDefinitionParserForOneArg<T>(SearchPattern.this.pattern ,firstArgPlaceholder, firstArgType, oneArgMethod));
            return SearchPattern.this;
        }

    }

    public class BindingTwoArgs<T, U> {
        private String firstArgPlaceholder;
        private String secondArgPlaceholder;
        private SearchPatterns.PlaceHolderType firstArgType;
        private SearchPatterns.PlaceHolderType secondArgType;

        public BindingTwoArgs(String firstArgPlaceholder, String secondArgPlaceholder, SearchPatterns.PlaceHolderType firstPlaceholderType, SearchPatterns.PlaceHolderType secondPlaceholderType){
            this.firstArgPlaceholder = firstArgPlaceholder;
            this.secondArgPlaceholder = secondArgPlaceholder;
            this.firstArgType = firstPlaceholderType;
            this.secondArgType = secondPlaceholderType;
        }

        public SearchPattern to(TwoArgMethod<T, U> twoArgMethod) {
            add(new StepDefinitionParserForTwoArg<T, U>(
                    SearchPattern.this.pattern ,
                    firstArgPlaceholder,
                    secondArgPlaceholder,
                    firstArgType,
                    secondArgType,
                    twoArgMethod));
            return SearchPattern.this;
        }


    }
}

