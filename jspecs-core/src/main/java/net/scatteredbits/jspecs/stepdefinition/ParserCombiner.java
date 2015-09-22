package net.scatteredbits.jspecs.stepdefinition;

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
                String oldPatternWithNewRegexp = newParser.cloneWith(p.getStringPattern().getOriginalPattern()).getStringPattern().getRegexpPatternWithoutGroupName();
                newStepDefParsers.add(p.cloneWith(oldPatternWithNewRegexp));
            }

            String newParserRegExpPattern = newParser.getStringPattern().getOriginalPattern();
            for (StepDefinitionParser p : stepDefParsers) {
                newParserRegExpPattern = p.cloneWith(newParserRegExpPattern).getStringPattern().getRegexpPatternWithoutGroupName();
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

    public void setParsers(List<StepDefinitionParser> parsers) {
        this.stepDefParsers = parsers;
    }
}
