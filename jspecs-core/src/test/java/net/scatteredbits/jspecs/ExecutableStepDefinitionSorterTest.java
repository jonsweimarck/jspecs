package net.scatteredbits.jspecs;

import net.scatteredbits.jspecs.stepdefinition.ComparableExecutableStepDefinition;
import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;
import net.scatteredbits.jspecs.stepdefinition.StepDefinitionParserForOneArg;
import org.junit.Test;

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
        StepDefinitionParserForOneArg parser1 = mock(StepDefinitionParserForOneArg.class);
        given(parser1.createExecutablesFrom(anyString())).willReturn(Arrays.asList(executable1));

        ComparableExecutableStepDefinition executable2 = new ComparableExecutableStepDefinition(1, null); // <-- should be ordered first
        StepDefinitionParserForOneArg parser2 = mock(StepDefinitionParserForOneArg.class);
        given(parser2.createExecutablesFrom(anyString())).willReturn(Arrays.asList(executable2));

        ComparableExecutableStepDefinition executable3 = new ComparableExecutableStepDefinition(2, null); // <- should be ordered second
        StepDefinitionParserForOneArg parser3 = mock(StepDefinitionParserForOneArg.class);
        given(parser3.createExecutablesFrom(anyString())).willReturn(Arrays.asList(executable3));

        // WHEN we try toTwoObjects match and and sort on matching order ...
        ParserCombiner pc1 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser1)).getMock();
        ParserCombiner pc2 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser2)).getMock();
        ParserCombiner pc3 = given(mock(ParserCombiner.class).getParsers()).willReturn(Arrays.asList(parser3)).getMock();
        List<ComparableExecutableStepDefinition> result = cut.sortOnMatches(Arrays.asList(pc1, pc2, pc3), "uninteresting keyExampleText");

        // THEN we will get the list of all three matches
        assertThat(result, hasSize(3));
        // And they should be ordered according toTwoObjects their mathing index, not the order they where passed toTwoObjects sortOnMatches
        assertThat(result.get(0), is(executable2));
        assertThat(result.get(1), is(executable3));
        assertThat(result.get(2), is(executable1));
    }
}