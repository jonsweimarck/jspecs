package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.RegExpMatchExtractor;

public class ComparableExecutableStepDefinition implements Comparable<ComparableExecutableStepDefinition>{

    private RegExpMatchExtractor regExpMatchExtractor;

    private Integer order;
    private ExecutableStepDefinition executableStepDefinition;

    public ComparableExecutableStepDefinition(int scenarioTextStart, ExecutableStepDefinition executableStepDefinition) {
        this.regExpMatchExtractor = new RegExpMatchExtractor();
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
