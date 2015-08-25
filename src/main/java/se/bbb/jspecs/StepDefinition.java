package se.bbb.jspecs;


import java.util.List;

public interface StepDefinition<T> {


    @FunctionalInterface
    interface StepDefinitionForObject<T> extends StepDefinition{
        void execute(T a1);
    }

    @FunctionalInterface
    public interface StepDefinitionForList<T>  extends StepDefinition{
        void execute(List<String> list);
    }
}
