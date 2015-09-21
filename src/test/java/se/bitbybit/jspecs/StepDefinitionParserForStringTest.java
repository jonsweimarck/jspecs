package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.ComparableExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParser;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParserForString;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitionParserForStringTest {

    private String stringResult;

    public void setString(String stringResult) {
        this.stringResult = stringResult;
    }



    @Test
    public void shouldCreateExecutableWhenStringIsAtMatchedStart(){

        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "{String} should be found",
                "{String}",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Tom should be found");
        executables.get(0).execute();
        assertThat(stringResult, is("Tom"));
    }

    @Test
    public void shouldCreateExecutableWhenStringISMatchedInTheMiddle(){

        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "The {String} should",
                "{String}",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("The string should be found");
        executables.get(0).execute();
        assertThat(stringResult, is("string"));
    }

    @Test
    public void shouldCreateExecutableWhenStringIsMatchedAtStart(){

        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "Should be found: {String}",
                "{String}",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Should be found: Tom");
        executables.get(0).execute();
        assertThat(stringResult, is("Tom"));
    }

    @Test
    public void shouldCreateExecutableWhenStringUsesUnicode(){

        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "Should bä found: {String}",
                "{String}",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Should bä found: Jöns");
        executables.get(0).execute();
        assertThat(stringResult, is("Jöns"));
    }

    @Test
    public void cloneWithShouldCloneButUseSpecifiedPattern(){

        String sharedOriginalPattern = "The strings {string1}, {string2} and {string3}";
        StepDefinitionParserForString parser1 = new StepDefinitionParserForString(sharedOriginalPattern, "{string1}", null);
        StepDefinitionParserForString parser3 = new StepDefinitionParserForString(sharedOriginalPattern, "{string3}", null);

        String parser1GroupName = extractGroupName(parser1);
        String parser2GroupName = extractGroupName(parser3);

        StepDefinitionParser p1Combined = parser1.cloneWith(parser3.getStringPattern().getRegexpPatternWithGroupName());
        StepDefinitionParser p3Combined = parser3.cloneWith(parser1.getStringPattern().getRegexpPatternWithGroupName());

        assertThat(p1Combined.getStringPattern().getOriginalPattern(), is("The strings {string1}, {string2} and (?<" + parser2GroupName  + ">\\w+)"));
        assertThat(p3Combined.getStringPattern().getOriginalPattern(), is("The strings (?<" + parser1GroupName  + ">\\w+), {string2} and {string3}"));
    }

    @Test
    public void shouldCreateTwoExecutablesWhenStringIsMatchedTwice(){
        StepDefinitionParserForString cut = new StepDefinitionParserForString(
                "be found: {String}",
                "{String}",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Should be found: Tom. Should also be found: Jerry");

        assertThat(executables, hasSize(2));

        executables.get(0).execute();
        assertThat(stringResult, is("Tom"));
        executables.get(1).execute();
        assertThat(stringResult, is("Jerry"));
    }

    private String extractGroupName(StepDefinitionParserForString parser) {
        String regexp = parser.getStringPattern().getRegexpPatternWithGroupName();
        String prefix = regexp.substring(0, regexp.indexOf("<") + 1);
        String suffix = regexp.substring(regexp.indexOf(">"));

        String result = regexp.replace(prefix, "").replace(suffix, "");
        return result;

    }

}