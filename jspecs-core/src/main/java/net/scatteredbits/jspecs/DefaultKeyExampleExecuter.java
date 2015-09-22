package net.scatteredbits.jspecs;

import net.scatteredbits.jspecs.stepdefinition.ComparableExecutableStepDefinition;
import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;

import java.util.Collection;
import java.util.List;

public class DefaultKeyExampleExecuter implements KeyExampleExecuter {

    private ExecutableStepDefinitionSorter executableStepDefinitionSorter;
//    private Predicate<Boolean> expectations;

    public DefaultKeyExampleExecuter(ExecutableStepDefinitionSorter executableStepDefinitionSorter){
        this.executableStepDefinitionSorter = executableStepDefinitionSorter;
    }

    @Override
    public void execute(String keyExample, Collection<ParserCombiner> parserCombiners) {
        List<ComparableExecutableStepDefinition> sorted = executableStepDefinitionSorter.sortOnMatches(parserCombiners, keyExample);

        for(ComparableExecutableStepDefinition oesd : sorted){
            oesd.execute();
        }

//        expectations.test()
    }
//
//    public KeyExampleExecuter withExpectations(Predicate<Boolean> expectations) {
//        this.expectations = expectations;
//        return this;
//    }
}
