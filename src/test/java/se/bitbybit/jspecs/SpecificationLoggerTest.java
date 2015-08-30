package se.bitbybit.jspecs;

import org.junit.Rule;
import org.junit.Test;
import se.bitbybit.jspecs.junitspecific.KeyExample;
import se.bitbybit.jspecs.junitspecific.Specification;
import se.bitbybit.jspecs.junitspecific.SpecificationLogger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

// Note: The tests checks that this annotation could be read
@Specification(name ="spec name", description = "spec desc")
public class SpecificationLoggerTest {

    @Rule
    public SpecificationLogger logger = new SpecificationLogger();

    @Test
    public void getSpecificationExampleShouldReturnDescriptionFromAnnotaionOnClass(){
        assertThat(logger.getSpecificationDescription(), is("spec desc"));
    }


    @Test
    @KeyExample(description = "spec example desc")
    public void getSpecificationDescriptionShouldReturnDescriptionFromAnnotaionOnMethod(){
        assertThat(logger.getKeyExample(), is("spec example desc"));
    }

}