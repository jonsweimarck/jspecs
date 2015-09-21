package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.ComparableExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.ExecutableStepDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ComparableExecutableStepDefinitionTest {


    @Test
    public void shouldBeSortedOnIndex(){
        ComparableExecutableStepDefinition oesd1 = new ComparableExecutableStepDefinition(1, null);
        ComparableExecutableStepDefinition oesd2 = new ComparableExecutableStepDefinition(2, null);
        ComparableExecutableStepDefinition oesd3 = new ComparableExecutableStepDefinition(3, null);

        List<ComparableExecutableStepDefinition> list = new ArrayList<>();

        list.add(oesd2);
        list.add(oesd3);
        list.add(oesd1);

        Collections.sort(list);

        assertThat(list.get(0), is(oesd1));
        assertThat(list.get(1), is(oesd2));
        assertThat(list.get(2), is(oesd3));
    }

    @Test
    public void executeShouldExecuteTheStepDeinition(){

        ExecutableStepDefinition esd = mock(ExecutableStepDefinition.class);

        ComparableExecutableStepDefinition oesd = new ComparableExecutableStepDefinition(1, esd);
        oesd.execute();

        verify(esd).execute();
    }


}