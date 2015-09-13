package se.bitbybit.jspecs.builders;

import se.bitbybit.jspecs.KeyExampleExecuter;
import se.bitbybit.jspecs.stepdefinition.ParserCombiner;

import java.util.Collection;
import java.util.HashSet;

public class ExecutableKeyExample {

    private String keyExampleText;
    private Collection<ParserCombiner> parserCombiners = new HashSet<>();

    private ExecutableKeyExample(String keyExampleText){
        this.keyExampleText = keyExampleText;
    }

    public static ExecutableKeyExample forKeyExample(String keyExample){
        return new ExecutableKeyExample(keyExample);
    }

    public ExecutableKeyExample withSearchPatterns(SearchPatterns searchPatterns){
        this.parserCombiners = searchPatterns.getParserCombiners();
        return this;
    }

    public void run(KeyExampleExecuter keyExampleExecuter) {
        keyExampleExecuter.execute(keyExampleText, parserCombiners);
    }
}
