package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.TwoArgMethod;

public class ExecutableStepDefinitionForTwoArg<T, U> implements ExecutableStepDefinition {

    private TwoArgMethod<T, U> stepDefinition;
    private T argT;
    private U argU;

    public ExecutableStepDefinitionForTwoArg(TwoArgMethod stepDef, T argT, U argU){
        this.stepDefinition = stepDef;
        this.argT = argT;
        this.argU = argU;
    }

    @Override
    public void execute() {
        stepDefinition.execute(argT, argU);
    }
}
