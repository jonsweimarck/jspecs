package se.bbb.jspecs;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepRunnerTest {

    private String foundMatch;

    @Test
    public void canRunSingleMatchingStepDef(){
        StepRunner sr = new StepRunner();

        setFoundMatch(null);
        sr.addStepDef(
                "(.*) is a String",
                (StepDefinition.StepDefinitionForObject<String>) foundString -> setFoundMatch(foundString));

        sr.runStepsOnSpecExample(new SpecificationSupplier(){
            public String getSpecificationExample(){
                return "This is a String";
            }
        });

        assertThat(foundMatch, is("This"));

    }

    public void setFoundMatch(String foundMatch) {
        this.foundMatch = foundMatch;
    }
}