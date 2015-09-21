package se.bitbybit.jspecsdemo;


import org.junit.Rule;
import org.junit.Test;
import se.bitbybit.jspecs.DefaultKeyExampleExecuter;
import se.bitbybit.jspecs.ExecutableStepDefinitionSorter;
import se.bitbybit.jspecs.builders.ExecutableKeyExample;
import se.bitbybit.jspecs.builders.SearchPatterns;
import se.bitbybit.jspecs.junitspecific.KeyExample;
import se.bitbybit.jspecs.junitspecific.SpecificationLogger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BasicTests {

    @Rule
    public SpecificationLogger logger = new SpecificationLogger();

    private DefaultKeyExampleExecuter defaultKeyExampleExecuter = new DefaultKeyExampleExecuter(new ExecutableStepDefinitionSorter());
    private String programmer;
    private String verb;

    public void setProgrammer(String programmer){
        this.programmer = programmer;
    }

    public void setVerb(String verb){
        this.verb = verb;
    }


    @KeyExample(description = "Jöns is programming this and have to fix all the bugs")
    @Test
    public void singleSearchPatternWithTwoBindings(){

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())

                .withSearchPatterns(new SearchPatterns()
                                .addSearchPattern("{programmer} is programming this and have to {verb}")
                                    .binding("{programmer}").toStringIn(programmer -> setProgrammer(programmer))
                                    .binding("{verb}").toStringIn(verb -> setVerb(verb))
                )

                .run(defaultKeyExampleExecuter);

        assertThat(programmer, is("Jöns"));
        assertThat(verb, is("fix"));
    }

}
