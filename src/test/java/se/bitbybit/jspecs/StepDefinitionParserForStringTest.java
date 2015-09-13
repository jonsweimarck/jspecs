package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.builders.PatternContainer;
import se.bitbybit.jspecs.stepdefinition.ExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParser;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParserForString;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitionParserForStringTest {

    private String stringResult;

    public void setString(String stringResult) {
        this.stringResult = stringResult;
    }

    @Test
    public void patternContainerShouldContainPatternFromConstructor(){
        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "{String} should be found",
                "{String}",
                null);

        PatternContainer patternContainer = cut.getStringPattern();

        assertThat(patternContainer.getOriginalPattern(), is("{String} should be found"));
        assertThat(patternContainer.getCompiledRegexpPattern().pattern(), is("(.*) should be found"));
    }


    @Test
    public void shouldParseOkWhenStringIsAtStart(){

        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "{String} should be found",
                "{String}",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        ExecutableStepDefinition executable = cut.parse("Tom should be found");
        executable.execute();
        assertThat(stringResult, is("Tom"));
    }

    @Test
    public void shouldParseOkWhenStringIsAtEnd(){

        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "Should be found: {String}",
                "{String}",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        ExecutableStepDefinition executable = cut.parse("Should be found: Tom");
        executable.execute();
        assertThat(stringResult, is("Tom"));
    }

    @Test
    public void cloneWithShouldCloneButUseSpecifiedPattern(){

        String sharedOriginalPattern = "The strings {string1}, {string2} and {string3}";
        StepDefinitionParserForString parser1 = new StepDefinitionParserForString(sharedOriginalPattern, "{string1}", null);
        StepDefinitionParserForString parser3 = new StepDefinitionParserForString(sharedOriginalPattern, "{string3}", null);


        StepDefinitionParser p1Combined = parser1.cloneWith(parser3.getStringPattern().getRegexpPattern());
        StepDefinitionParser p3Combined = parser3.cloneWith(parser1.getStringPattern().getRegexpPattern());

        assertThat(p1Combined.getStringPattern().getOriginalPattern(), is("The strings {string1}, {string2} and (.*)"));
        assertThat(p3Combined.getStringPattern().getOriginalPattern(), is("The strings (.*), {string2} and {string3}"));

        assertThat(p1Combined.getStringPattern().getRegexpPattern(), is("The strings (.*), {string2} and (.*)"));
        assertThat(p3Combined.getStringPattern().getRegexpPattern(), is("The strings (.*), {string2} and (.*)"));
    }

}