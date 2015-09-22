package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.KeyExampleExecuter;
import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;

import java.util.ArrayList;
import java.util.Collection;

public class ExecutableKeyExample {

    private String keyExampleText;
    private Collection<ParserCombiner> parserCombiners = new ArrayList<>();
    private TableSearchConverter tableSearchConverter = new TableSearchConverter();

    private ExecutableKeyExample(String keyExampleText){
        this.keyExampleText = keyExampleText;
    }

    public static ExecutableKeyExample forKeyExample(String keyExample){
        return new ExecutableKeyExample(keyExample);
    }

    public ExecutableKeyExample withSearchPatterns(SearchPatterns searchPatterns){
        this.parserCombiners = searchPatterns.getParserCombiners();
        this.parserCombiners.addAll(tableSearchConverter.convert(keyExampleText, searchPatterns.getTableSearches()));
        return this;
    }

    public void run(KeyExampleExecuter keyExampleExecuter) {
        keyExampleExecuter.execute(keyExampleText, parserCombiners);
    }

    // For use in tests
    protected ExecutableKeyExample withTableSearchConverter(TableSearchConverter converter) {
        this.tableSearchConverter = converter;
        return this;
    }
}
