package se.bitbybit.jspecs.stepdefinition;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ParserCombinerTest {

    @Test
    public void shouldThrowIfNotAllParsersShareTheSameOriginalPattern(){
        throw new NoSuchMethodError();
    }

    @Test
    public void shouldCombineTwoParsers(){

        String sharedOriginalPattern = "The strings {string1} and {string2}";
        StepDefinitionParserForString parser1 = new StepDefinitionParserForString(sharedOriginalPattern, "{string1}", null);
        StepDefinitionParserForString parser2 = new StepDefinitionParserForString(sharedOriginalPattern, "{string2}", null);

        List<StepDefinitionParser> combinedParsers = new ParserCombiner().add(parser1).add(parser2).getParsers();

        assertThat(combinedParsers, hasSize(2));
        for(StepDefinitionParser parser : combinedParsers){
            assertThat("Mismatch for parser with placeholder " + parser.getPlaceholder(),
                    parser.getStringPattern().getRegexpPattern(), is("The strings (.*) and (.*)"));

            switch(parser.getPlaceholder()){
                case "{string1}" : assertThat(parser.getStringPattern().getOriginalPattern(), is("The strings {string1} and (.*)")); break;
                case "{string2}" : assertThat(parser.getStringPattern().getOriginalPattern(), is("The strings (.*) and {string2}")); break;
                default: fail("Unknown placeholder: " + parser.getPlaceholder());
            }
        }
    }

    @Test
    public void shouldCombineThreeParsers(){

        String sharedOriginalPattern = "The strings {string1}, {string2} and {string3}";
        StepDefinitionParserForString parser1 = new StepDefinitionParserForString(sharedOriginalPattern, "{string1}", null);
        StepDefinitionParserForString parser2 = new StepDefinitionParserForString(sharedOriginalPattern, "{string2}", null);
        StepDefinitionParserForString parser3 = new StepDefinitionParserForString(sharedOriginalPattern, "{string3}", null);

        List<StepDefinitionParser> combinedParsers = new ParserCombiner().add(parser1).add(parser2).add(parser3).getParsers();

        assertThat(combinedParsers, hasSize(3));
        for(StepDefinitionParser parser : combinedParsers){
            assertThat("Mismatch for parser with placeholder " + parser.getPlaceholder(),
                    parser.getStringPattern().getRegexpPattern(), is("The strings (.*), (.*) and (.*)"));

            switch(parser.getPlaceholder()){
                case "{string1}" : assertThat(parser.getStringPattern().getOriginalPattern(), is("The strings {string1}, (.*) and (.*)")); break;
                case "{string2}" : assertThat(parser.getStringPattern().getOriginalPattern(), is("The strings (.*), {string2} and (.*)")); break;
                case "{string3}" : assertThat(parser.getStringPattern().getOriginalPattern(), is("The strings (.*), (.*) and {string3}")); break;
                default: fail("Unknown placeholder: " + parser.getPlaceholder());
            }
        }
    }
}