package se.bbb.jspecs;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitionRegexpParserForObjectTest {

    private String stringResult;

    @Test
    public void shouldFindStringAtStart(){


        StepDefinitionRegexpParserForObject cut = new StepDefinitionRegexpParserForObject(
                "{String} should be found",
                (StepDefinition.StepDefinitionForObject<String>)arg -> setString(arg)
        );

        ExecutableStepDefinition executable = cut.parse("Tom should be found");
        executable.execute();
        assertThat(stringResult, is("Tom"));
    }

    public void setString(String stringResult) {
        this.stringResult = stringResult;
    }
}