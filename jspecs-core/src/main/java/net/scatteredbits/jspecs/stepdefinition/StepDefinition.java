package net.scatteredbits.jspecs.stepdefinition;


import java.util.List;

public interface StepDefinition<T, U> {


    @FunctionalInterface
    interface ForObject extends StepDefinition{
        void execute(Object a1);
    }

    @FunctionalInterface
    interface ForList extends StepDefinition{
        void execute(List<Object> a1);
    }

    @FunctionalInterface
    interface StepDefinitionForObject<T> extends StepDefinition{
        void execute(T a1);
    }

    @FunctionalInterface
    interface StepDefinitionForList<T>  extends StepDefinition{
        void execute(List<T> list);
    }

    @FunctionalInterface
    interface StepDefinitionForTwoObjects<T, U> extends StepDefinition{
        void execute(T a1, U a2);
    }

    @FunctionalInterface
    interface StepDefinitionForOneObjectOneList<T, U> extends StepDefinition{
        void execute(T a1, List<U> a2);
    }

    @FunctionalInterface
    interface StepDefinitionForOneListOneObject<T, U> extends StepDefinition{
        void execute(List<T> a1, U a2);
    }
}
