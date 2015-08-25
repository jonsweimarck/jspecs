package se.bbb.jspecs;

public class OrderedExecutableStepDefinition {

    private RegExpMatchExtractor regExpMatchExtractor;

    private int order;
    private ExecutableStepDefinition executableStepDefinition;

    public OrderedExecutableStepDefinition(int scenarioTextStart, ExecutableStepDefinition executableStepDefinition) {
        this.regExpMatchExtractor = new RegExpMatchExtractor();
        this.order = scenarioTextStart;
        this.executableStepDefinition = executableStepDefinition;
    }

    public void execute() {
        throw new NoSuchMethodError();
    }
}
