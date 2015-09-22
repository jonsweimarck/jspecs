package net.scatteredbits.jspecs.stepdefinition;

public class ExecutableStepDefinitionForObject<T> implements ExecutableStepDefinition {

    private StepDefinition.StepDefinitionForObject stepDefinitionForObject;
    private T arg;

    public ExecutableStepDefinitionForObject(StepDefinition.StepDefinitionForObject stepDef, T arg){
        this.stepDefinitionForObject = stepDef;
        this.arg = arg;
    }

    @Override
    public void execute() {
        stepDefinitionForObject.execute(arg);
    }
}
