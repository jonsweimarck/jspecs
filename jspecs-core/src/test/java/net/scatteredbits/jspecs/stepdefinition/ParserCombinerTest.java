package net.scatteredbits.jspecs.stepdefinition;

import org.junit.Test;

import java.util.List;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ParserCombinerTest {

    @Test
    public void shouldCombineTwoParsers(){

        String sharedOriginalPattern = "The strings {string1} and {string2}";
        StepDefinitionParserForOneArg parser1 = new StepDefinitionParserForOneArg(sharedOriginalPattern, "{string1}", asString,  null);
        StepDefinitionParserForOneArg parser2 = new StepDefinitionParserForOneArg(sharedOriginalPattern, "{string2}", asString, null);

        List<StepDefinitionParser> combinedParsers = new ParserCombiner().add(parser1).add(parser2).getParsers();

        String regExpPatternP1 = extractRegExpPatter(combinedParsers.get(0));
        String regExpPatternP2 = extractRegExpPatter(combinedParsers.get(1));

        assertThat(combinedParsers, hasSize(2));
        for(StepDefinitionParser parser : combinedParsers){

            switch(parser.getPlaceholderT()) {
                case "{string1}":
                    assertThat(parser.getStringPattern().getRegexpPatternWithGroupName(), is("The strings " + regExpPatternP1 + " and (\\w+)"));
                    break;

                case "{string2}":
                    assertThat(parser.getStringPattern().getRegexpPatternWithGroupName(), is("The strings (\\w+) and " + regExpPatternP2));
                    break;

                default:
                    fail("Unknown placeholder: " + parser.getPlaceholderT());
            }

        }
    }

    @Test
    public void shouldCombineThreeParsers() {

        String sharedOriginalPattern = "The strings {string1}, {string2} and {string3}";
        StepDefinitionParserForOneArg parser1 = new StepDefinitionParserForOneArg(sharedOriginalPattern, "{string1}",asString, null);
        StepDefinitionParserForOneArg parser2 = new StepDefinitionParserForOneArg(sharedOriginalPattern, "{string2}",asString, null);
        StepDefinitionParserForOneArg parser3 = new StepDefinitionParserForOneArg(sharedOriginalPattern, "{string3}",asString, null);

        List<StepDefinitionParser> combinedParsers = new ParserCombiner().add(parser1).add(parser2).add(parser3).getParsers();

        assertThat(combinedParsers, hasSize(3));
        String regExpPatternP1 = extractRegExpPatter(combinedParsers.get(0));
        String regExpPatternP2 = extractRegExpPatter(combinedParsers.get(1));
        String regExpPatternP3 = extractRegExpPatter(combinedParsers.get(2));


        for (StepDefinitionParser parser : combinedParsers) {
            switch (parser.getPlaceholderT()) {
                case "{string1}":
                    assertThat(parser.getStringPattern().getRegexpPatternWithGroupName(), is("The strings " + regExpPatternP1 + ", (\\w+) and (\\w+)"));
                    break;

                case "{string2}":
                    assertThat(parser.getStringPattern().getRegexpPatternWithGroupName(), is("The strings (\\w+), " + regExpPatternP2 + " and (\\w+)"));
                    break;

                case "{string3}":
                    assertThat(parser.getStringPattern().getRegexpPatternWithGroupName(), is("The strings (\\w+), (\\w+) and " + regExpPatternP3));
                    break;

                default:
                    fail("Unknown placeholder: " + parser.getPlaceholderT());
            }
        }
    }

    private String extractRegExpPatter(StepDefinitionParser parser) {
        String regexp = parser.getStringPattern().getRegexpPatternWithGroupName();
        String prefix = regexp.substring(0, regexp.indexOf("<") - 2);
        String suffix = regexp.substring(regexp.indexOf(">") + 5);

        String result = regexp.replace(prefix, "").replace(suffix, "");
        return result;
    }
}