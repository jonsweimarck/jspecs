package se.bitbybit.jspecs.stepdefinition;

import se.bitbybit.jspecs.RegExpMatchExtractor;

public class OrderedExecutableStepDefinition implements Comparable<OrderedExecutableStepDefinition>{

    private RegExpMatchExtractor regExpMatchExtractor;

    private Integer order;
    private ExecutableStepDefinition executableStepDefinition;

    public OrderedExecutableStepDefinition(int scenarioTextStart, ExecutableStepDefinition executableStepDefinition) {
        this.regExpMatchExtractor = new RegExpMatchExtractor();
        this.order = scenarioTextStart;
        this.executableStepDefinition = executableStepDefinition;
    }

    public void execute() {
        executableStepDefinition.execute();
    }

    @Override
    public int compareTo(OrderedExecutableStepDefinition orderedExecutableStepDefinition) {
        return this.order.compareTo(orderedExecutableStepDefinition.order);
    }
}
