package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.Method;

public class ExecutableStepDefinitionForOneArg<T, Object> implements ExecutableStepDefinition {

    private Method<T, Object> stepDefinition;
    private T arg1;

    public ExecutableStepDefinitionForOneArg(Method stepDef, T arg1){
        this.stepDefinition = stepDef;
        this.arg1 = arg1;
    }

    @Override
    public void execute() {
        stepDefinition.execute(arg1);
    }
}
