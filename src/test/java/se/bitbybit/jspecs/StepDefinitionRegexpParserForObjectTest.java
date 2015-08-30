package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.ExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParserForObject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitionRegexpParserForObjectTest {

    private String stringResult;

    public void setString(String stringResult) {
        this.stringResult = stringResult;
    }

    @Test
    public void shouldFindStringAtStart(){


        StepDefinitionRegexpParserForObject cut = new StepDefinitionRegexpParserForObject(
                "{String} should be found",
                (StepDefinition.StepDefinitionForObject<String>) arg -> setString(arg)
        );

        ExecutableStepDefinition executable = cut.parse("Tom should be found");
        executable.execute();
        assertThat(stringResult, is("Tom"));
    }

    @Test
    public void shouldCreateRegExpForString() {
        StepDefinitionRegexpParserForObject cut = new StepDefinitionRegexpParserForObject("{String} should be found", null);
        assertThat(cut.patternAsRegexp().pattern(), is("(.*) should be found"));
    }


}