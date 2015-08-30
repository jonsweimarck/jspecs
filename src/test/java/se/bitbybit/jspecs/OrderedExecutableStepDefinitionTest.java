package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.ExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.OrderedExecutableStepDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OrderedExecutableStepDefinitionTest {


    @Test
    public void shouldBeSortedOnIndex(){
        OrderedExecutableStepDefinition oesd1 = new OrderedExecutableStepDefinition(1, null);
        OrderedExecutableStepDefinition oesd2 = new OrderedExecutableStepDefinition(2, null);
        OrderedExecutableStepDefinition oesd3 = new OrderedExecutableStepDefinition(3, null);

        List<OrderedExecutableStepDefinition> list = new ArrayList<>();

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

        OrderedExecutableStepDefinition oesd = new OrderedExecutableStepDefinition(1, esd);
        oesd.execute();

        verify(esd).execute();
    }


}