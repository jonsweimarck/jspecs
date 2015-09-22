package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.OneArgMethod;
import org.junit.Test;

import java.util.List;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asInteger;
import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitionParserForOneArgTest {
    private String stringResult;
    private Integer integerResult;

    public void setString(String stringResult) {
        this.stringResult = stringResult;
    }
    public void setInteger(Integer integerResult) {
        this.integerResult = integerResult;
    }


    @Test
    public void shouldCreateExecutableWhenIntegerIsMatched(){
        StepDefinitionParserForOneArg<Integer> cut = new StepDefinitionParserForOneArg<>(
                "number {Integer} should be found",
                "{Integer}",
                asInteger,
                new OneArgMethod<>(this::setInteger)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("The number 42 should be found");
        executables.get(0).execute();
        assertThat(integerResult, is(42));
    }

    @Test
    public void shouldCreateExecutableWhenStringIsMatchedAtStart(){
        StepDefinitionParserForOneArg<String> cut = new StepDefinitionParserForOneArg<>(
                "{String} should be found",
                "{String}",
                asString,
                new OneArgMethod<>(this::setString)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Tom should be found");
        executables.get(0).execute();
        assertThat(stringResult, is("Tom"));
    }


    @Test
    public void shouldCreateExecutableWhenStringIsMatchedInTheMiddle(){
        StepDefinitionParserForOneArg<String> cut = new StepDefinitionParserForOneArg<>(
                "The {String} should",
                "{String}",
                asString,
                new OneArgMethod<>(this::setString)
        );


        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("The string should be found");
        executables.get(0).execute();
        assertThat(stringResult, is("string"));
    }

    @Test
    public void shouldCreateExecutableWhenStringIsMatchedAtEnd(){
        StepDefinitionParserForOneArg<String> cut = new StepDefinitionParserForOneArg<>(
                "Should be found: {String}",
                "{String}",
                asString,
                new OneArgMethod<>(this::setString)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Should be found: Tom");
        executables.get(0).execute();
        assertThat(stringResult, is("Tom"));
    }

    @Test
    public void shouldCreateExecutableWhenStringIsMatchedImTextWithPipeCharacters(){
        StepDefinitionParserForOneArg<String> cut = new StepDefinitionParserForOneArg<>(
                "Should be | found: {String} |",
                "{String}",
                asString,
                new OneArgMethod<>(this::setString)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Should be | found: Tom |");
        executables.get(0).execute();
        assertThat(stringResult, is("Tom"));
    }

    @Test
    public void shouldCreateExecutableWhenStringUsesUnicode(){
        StepDefinitionParserForOneArg<String> cut = new StepDefinitionParserForOneArg<>(
                "Should bä found: {String}",
                "{String}",
                asString,
                new OneArgMethod<>(this::setString)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Should bä found: Jöns");
        executables.get(0).execute();
        assertThat(stringResult, is("Jöns"));
    }

    @Test
    public void cloneWithShouldCloneButUseSpecifiedPattern(){

        String sharedOriginalPattern = "The strings {string1}, {string2} and {string3}";
        StepDefinitionParserForOneArg<String> parser1 = new StepDefinitionParserForOneArg<>(sharedOriginalPattern, "{string1}", asString, null);
        StepDefinitionParserForOneArg<String> parser3 = new StepDefinitionParserForOneArg<>(sharedOriginalPattern, "{string3}", asString, null);

        String parser1GroupName = extractGroupName(parser1);
        String parser2GroupName = extractGroupName(parser3);

        StepDefinitionParser p1Combined = parser1.cloneWith(parser3.getStringPattern().getRegexpPatternWithGroupName());
        StepDefinitionParser p3Combined = parser3.cloneWith(parser1.getStringPattern().getRegexpPatternWithGroupName());

        assertThat(p1Combined.getStringPattern().getOriginalPattern(), is("The strings {string1}, {string2} and (?<" + parser2GroupName  + ">\\w+)"));
        assertThat(p3Combined.getStringPattern().getOriginalPattern(), is("The strings (?<" + parser1GroupName  + ">\\w+), {string2} and {string3}"));
    }

    @Test
    public void shouldCreateTwoExecutablesWhenStringIsMatchedTwice(){
        StepDefinitionParserForOneArg<String> cut = new StepDefinitionParserForOneArg<>(
                "be found: {String}",
                "{String}",
                asString,
                new OneArgMethod<>(this::setString)
        );

        List<ComparableExecutableStepDefinition> executables = cut.createExecutablesFrom("Should be found: Tom. Should also be found: Jerry");

        assertThat(executables, hasSize(2));

        executables.get(0).execute();
        assertThat(stringResult, is("Tom"));
        executables.get(1).execute();
        assertThat(stringResult, is("Jerry"));
    }

    private String extractGroupName(StepDefinitionParserForOneArg parser) {
        String regexp = parser.getStringPattern().getRegexpPatternWithGroupName();
        String prefix = regexp.substring(0, regexp.indexOf("<") + 1);
        String suffix = regexp.substring(regexp.indexOf(">"));

        String result = regexp.replace(prefix, "").replace(suffix, "");
        return result;
    }


}