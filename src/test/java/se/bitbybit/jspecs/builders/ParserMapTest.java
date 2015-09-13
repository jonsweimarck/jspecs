package se.bitbybit.jspecs.builders;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.OrderedExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParserForString;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ParserMapTest {

    private String placeholder1;
    private String placeholder2;

    @Test
    public void sdads() {
        StepDefinitionParserForString stepDefParser1
                = new StepDefinitionParserForString(
                "A text with {placeholder1} and {placeholder2}",
                "{placeholder1}",
                (StepDefinition.StepDefinitionForObject<String>)  placeholder1 -> setPlaceholder1(placeholder1));

        StepDefinitionParserForString stepDefParser2
                = new StepDefinitionParserForString(
                "A text with {placeholder1} and {placeholder2}",
                "{placeholder2}",
                (StepDefinition.StepDefinitionForObject<String>)  placeholder2 -> setPlaceholder2(placeholder2));

        ParserMap pm = new ParserMap(Arrays.asList(stepDefParser1, stepDefParser2));

        List<OrderedExecutableStepDefinition> executableStepDefs= pm.getExecutableStepDefsFor("This is a text with StringNo1 and StringNo2 inside");

        assertThat(executableStepDefs, hasSize(2));

        assertThat(placeholder1, is(nullValue()));
        executableStepDefs.get(0).execute();
        assertThat(placeholder1, is("StringNo1"));

        assertThat(placeholder2, is(nullValue()));
        executableStepDefs.get(1).execute();
        assertThat(placeholder2, is("StringNo2"));


    }


    public void setPlaceholder1(String placeholder1) {
        this.placeholder1 = placeholder1;
    }
    public void setPlaceholder2(String placeholder2) {
        this.placeholder2 = placeholder2;
    }
}