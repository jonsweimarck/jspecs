package se.bitbybit.jspecs.stepdefinition;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ParserCombinerTest {

    @Test
    public void shouldCombineTwoParsers(){

        String sharedOriginalPattern = "The strings {string1} and {string2}";
        StepDefinitionParserForString parser1 = new StepDefinitionParserForString(sharedOriginalPattern, "{string1}", null);
        StepDefinitionParserForString parser2 = new StepDefinitionParserForString(sharedOriginalPattern, "{string2}", null);

        List<StepDefinitionParser> combinedParsers = new ParserCombiner().add(parser1).add(parser2).getParsers();

        String regExpPatternP1 = extractRegExpPatter(combinedParsers.get(0));
        String regExpPatternP2 = extractRegExpPatter(combinedParsers.get(1));

        assertThat(combinedParsers, hasSize(2));
        for(StepDefinitionParser parser : combinedParsers){

            switch(parser.getPlaceholder()) {
                case "{string1}":
                    assertThat(parser.getStringPattern().getRegexpPatternWithGroupName(), is("The strings " + regExpPatternP1 + " and (\\w+)"));
                    break;

                case "{string2}":
                    assertThat(parser.getStringPattern().getRegexpPatternWithGroupName(), is("The strings (\\w+) and " + regExpPatternP2));
                    break;

                default:
                    fail("Unknown placeholder: " + parser.getPlaceholder());
            }

        }
    }

    @Test
    public void shouldCombineThreeParsers() {

        String sharedOriginalPattern = "The strings {string1}, {string2} and {string3}";
        StepDefinitionParserForString parser1 = new StepDefinitionParserForString(sharedOriginalPattern, "{string1}", null);
        StepDefinitionParserForString parser2 = new StepDefinitionParserForString(sharedOriginalPattern, "{string2}", null);
        StepDefinitionParserForString parser3 = new StepDefinitionParserForString(sharedOriginalPattern, "{string3}", null);

        List<StepDefinitionParser> combinedParsers = new ParserCombiner().add(parser1).add(parser2).add(parser3).getParsers();

        assertThat(combinedParsers, hasSize(3));
        String regExpPatternP1 = extractRegExpPatter(combinedParsers.get(0));
        String regExpPatternP2 = extractRegExpPatter(combinedParsers.get(1));
        String regExpPatternP3 = extractRegExpPatter(combinedParsers.get(2));


        for (StepDefinitionParser parser : combinedParsers) {
            switch (parser.getPlaceholder()) {
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
                    fail("Unknown placeholder: " + parser.getPlaceholder());
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