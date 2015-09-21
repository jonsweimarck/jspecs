package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.ComparableExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.ParserCombiner;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParserForString;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

public class ExecutableStepDefinitionSorterTest {

    ExecutableStepDefinitionSorter cut = new ExecutableStepDefinitionSorter();


    @Test
    public void shouldReturn_ComparableExecutableStepDefs_sortedOnMatch() {
        // GIVEN three parsers that each will match and return a ComparableExecutableStepDefinition
        ComparableExecutableStepDefinition executable1 = new ComparableExecutableStepDefinition(3, null); // <-- should be ordered third
        StepDefinitionParserForString parser1 = mock(StepDefinitionParserForString.class);
        given(parser1.createExecutablesFrom(anyString())).willReturn(Arrays.asList(executable1));

        ComparableExecutableStepDefinition executable2 = new ComparableExecutableStepDefinition(1, null); // <-- should be ordered first
        StepDefinitionParserForString parser2 = mock(StepDefinitionParserForString.class);
        given(parser2.createExecutablesFrom(anyString())).willReturn(Arrays.asList(executable2));

        ComparableExecutableStepDefinition executable3 = new ComparableExecutableStepDefinition(2, null); // <- should be ordered second
        StepDefinitionParserForString parser3 = mock(StepDefinitionParserForString.class);
        given(parser3.createExecutablesFrom(anyString())).willReturn(Arrays.asList(executable3));

        // WHEN we try to match and and sort on matching order ...
        ParserCombiner pc1 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser1)).getMock();
        ParserCombiner pc2 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser2)).getMock();
        ParserCombiner pc3 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser3)).getMock();
        List<ComparableExecutableStepDefinition> result = cut.sortOnMatches(Arrays.asList(pc1, pc2, pc3), "uninteresting keyExampleText");

        // THEN we will get the list of all three matches
        assertThat(result, hasSize(3));
        // And they should be ordered according to their mathing index, not the order they where passed to sortOnMatches
        assertThat(result.get(0), is(executable2));
        assertThat(result.get(1), is(executable3));
        assertThat(result.get(2), is(executable1));
    }
}