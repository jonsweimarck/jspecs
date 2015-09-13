package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.builders.PatternContainer;
import se.bitbybit.jspecs.stepdefinition.ExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.OrderedExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.ParserCombiner;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParserForString;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ExecutableStepDefinitionSorterTest {

    ExecutableStepDefinitionSorter cut = new ExecutableStepDefinitionSorter();

    @Test
    public void shouldMatchaAndSortOnTheMatchingOrder(){

        String keyExampleText = "A key example text";

        String matchingString1 = "key example";
        String matchingString2 = "A key";
        String matchingString3 = "key";


        // parser1 will match on matchedString1 which will make executable1 end up in the sorted list
        ExecutableStepDefinition executable1 = mock(ExecutableStepDefinition.class);
        StepDefinitionParserForString parser1 = mock(StepDefinitionParserForString.class);
        given(parser1.getStringPattern()).willReturn(new PatternContainer("", matchingString1));
        given(parser1.parse(anyString())).willReturn(executable1);

        // parser2 will match on matchedString2 which will make executable2 end up in the sorted list
        ExecutableStepDefinition executable2 = mock(ExecutableStepDefinition.class);
        StepDefinitionParserForString parser2 = mock(StepDefinitionParserForString.class);
        given(parser2.getStringPattern()).willReturn(new PatternContainer("", matchingString2));
        given(parser2.parse(anyString())).willReturn(executable2);

        // parser3 will match on matchedString3 which will make executable3 end up in the sorted list
        ExecutableStepDefinition executable3 = mock(ExecutableStepDefinition.class);
        StepDefinitionParserForString parser3 = mock(StepDefinitionParserForString.class);
        given(parser3.getStringPattern()).willReturn(new PatternContainer("", matchingString3));
        given(parser3.parse(anyString())).willReturn(executable3);

        // When we try to match and and sort on matching order ...
        ParserCombiner pc1 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser1)).getMock();
        ParserCombiner pc2 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser2)).getMock();
        ParserCombiner pc3 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser3)).getMock();
        List<OrderedExecutableStepDefinition> result = cut.sortOnMatches(Arrays.asList(pc1, pc2, pc3), keyExampleText);

        // ... we will get a list of all three matches
        assertThat(result, hasSize(3));

        // Just to be sure, none of them should have been called
        verify(executable1, never()).execute();
        verify(executable2, never()).execute();
        verify(executable3, never()).execute();

        // ... But if we call them we can check that they are sorted in the order of the matchingStrings
        result.get(0).execute();
        verify(executable2, times(1)).execute();

        result.get(1).execute();
        verify(executable1, times(1)).execute();

        result.get(2).execute();
        verify(executable3, times(1)).execute();
    }

    @Test
    public void shouldGiveTwoMatchesIfTheStringMatchesTwice(){

        String keyExampleText = "A key example text with the substring 'key example' twice";
        String matchingString = "key example";

        // parser will match on matchedString which will make executable end up in the sorted list
        ExecutableStepDefinition executable = mock(ExecutableStepDefinition.class);
        StepDefinitionParserForString parser = mock(StepDefinitionParserForString.class);
        given(parser.getStringPattern()).willReturn(new PatternContainer("", matchingString));
        given(parser.parse(anyString())).willReturn(executable);

        // When we try to match and and sort on matching order ...
        ParserCombiner pc = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser)).getMock();
        List<OrderedExecutableStepDefinition> result = cut.sortOnMatches(Arrays.asList(pc), keyExampleText);

        // ... we will get a list of TWO matches, because the sting is matched twice!
        assertThat(result, hasSize(2));
    }

}