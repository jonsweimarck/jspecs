package se.bbb.jspecs;

import java.util.List;

public class ExecutableStepDefinitionForList<T> implements ExecutableStepDefinition {

    private StepDefinition.StepDefinitionForList stepDefinitionForList;
    private List<T> arg;

    public ExecutableStepDefinitionForList(StepDefinition.StepDefinitionForList stepDef, List<T> arg){
        this.stepDefinitionForList = stepDef;
        this.arg = arg;
    }

    @Override
    public void execute() {
        stepDefinitionForList.execute(arg);
    }
}
