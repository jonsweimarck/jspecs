package se.bitbybit.jspecs.stepdefinition;

import java.util.ArrayList;
import java.util.List;

public class ParserCombiner {

    public ParserCombiner(){}

    private List<StepDefinitionParser> stepDefParsers = new ArrayList<>();

    public ParserCombiner add(StepDefinitionParser newParser) {
        List<StepDefinitionParser> newStepDefParsers = new ArrayList<>();
        if(stepDefParsers.isEmpty()){
            newStepDefParsers.add(newParser);
        } else {
            for (StepDefinitionParser p : stepDefParsers) {
                String oldPatternWithNewRegexp = newParser.cloneWith(p.getStringPattern().getOriginalPattern()).getStringPattern().getRegexpPattern();
                newStepDefParsers.add(p.cloneWith(oldPatternWithNewRegexp));
            }

            String newParserRegExpPattern = newParser.getStringPattern().getOriginalPattern();
            for (StepDefinitionParser p : stepDefParsers) {
                newParserRegExpPattern = p.cloneWith(newParserRegExpPattern).getStringPattern().getRegexpPattern();
            }
            newStepDefParsers.add(newParser.cloneWith(newParserRegExpPattern));
        }
        ParserCombiner pc = new ParserCombiner();
        pc.stepDefParsers = newStepDefParsers;
        return pc;
    }

    public List<StepDefinitionParser> getParsers() {
        return this.stepDefParsers;
    }
}