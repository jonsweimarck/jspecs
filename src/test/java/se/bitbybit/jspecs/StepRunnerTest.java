package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.StepDefinition;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepRunnerTest {

    private String foundMatch;

    @Test
    public void canRunSingleMatchingStepDef(){
        StepRunner sr = new StepRunner();

        setFoundMatch(null);
        sr.addStepDef(
                "{String} should be found",
                (StepDefinition.StepDefinitionForObject<String>) foundString -> setFoundMatch(foundString));

        sr.runStepsOnSpecExample(new SpecificationSupplier(){
            public String getSpecificationDescription() {
                return null;
            }

            public String getKeyExample(){
                return "This should be found";
            }
        });

        assertThat(foundMatch, is("This"));

    }

    public void setFoundMatch(String foundMatch) {
        this.foundMatch = foundMatch;
    }
}