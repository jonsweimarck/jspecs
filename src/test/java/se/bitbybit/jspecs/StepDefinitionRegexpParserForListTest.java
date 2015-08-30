package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParserForList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitionRegexpParserForListTest {

    @Test
    public void shouldCreateRegExpForListOfString() {
        StepDefinitionRegexpParserForList cut = new StepDefinitionRegexpParserForList("a list: {list<string>}", null);
        assertThat(cut.patternAsRegexp().pattern(), is("a list: \\* ([A-Z])\\w+"));
    }

}