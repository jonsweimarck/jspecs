package net.scatteredbits.jspecs.stepdefinition;

public class ComparableExecutableStepDefinition implements Comparable<ComparableExecutableStepDefinition>{


    private Integer order;
    private ExecutableStepDefinition executableStepDefinition;

    public ComparableExecutableStepDefinition(int scenarioTextStart, ExecutableStepDefinition executableStepDefinition) {
        this.order = scenarioTextStart;
        this.executableStepDefinition = executableStepDefinition;
    }

    public void execute() {
        executableStepDefinition.execute();
    }

    @Override
    public int compareTo(ComparableExecutableStepDefinition comparableExecutableStepDefinition) {
        return this.order.compareTo(comparableExecutableStepDefinition.order);
    }
}
