package net.scatteredbits.jspecs;

import net.scatteredbits.jspecs.stepdefinition.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class DefaultKeyExampleExecuterTest {

    private StepDefinitionParserForOneArg parser1 = new StepDefinitionParserForOneArg("pattern1", "placeholder1", asString, null);
    private StepDefinitionParserForOneArg parser2 = new StepDefinitionParserForOneArg("pattern2", "placeholder2", asString, null);
    private String string1;
    private String string2;

    private void setString1(Object string1){
        this.string1 = (String)string1;
    }

    private void setString2(Object string2){
        this.string2 = (String)string2;
    }

    @Test
    public void shouldCallSorterWithParserCombiners(){

        FakedExecutableStepDefinitionSorter fakedExecutableStepDefinitionSorter = new FakedExecutableStepDefinitionSorter();

        DefaultKeyExampleExecuter cut = new DefaultKeyExampleExecuter(fakedExecutableStepDefinitionSorter);
        ParserCombiner pc = new ParserCombiner().add(parser1).add(parser2);
        cut.execute("some text", Arrays.asList(pc));

        assertThat(fakedExecutableStepDefinitionSorter.wasCalledWithParser1, is(true));
        assertThat(fakedExecutableStepDefinitionSorter.wasCalledWithParser2, is(true));
    }

    @Test
    public void shouldExecuteTheExecutableStepDefinitionsFromTheSorter(){
        FakedExecutableStepDefinitionSorter2 fakedExecutableStepDefinitionSorter = new FakedExecutableStepDefinitionSorter2();

        DefaultKeyExampleExecuter cut = new DefaultKeyExampleExecuter(fakedExecutableStepDefinitionSorter);
        cut.execute("some text", Arrays.asList(new ParserCombiner()));

        assertThat(string1, is("I'm set"));
        assertThat(string2, is("I'm set too"));
    }



    public class FakedExecutableStepDefinitionSorter extends ExecutableStepDefinitionSorter{
        public boolean wasCalledWithParser1 = false;
        public boolean wasCalledWithParser2 = false;

        @Override
        public List<ComparableExecutableStepDefinition> sortOnMatches(Collection<ParserCombiner> parserCombiners, String keyExampleText) {
            ParserCombiner pc = parserCombiners.iterator().next();
            if(pc.getParsers().get(0).getPlaceholderT().equals("placeholder1")){
                wasCalledWithParser1 = true;
            }
            if(pc.getParsers().get(0).getPlaceholderT().equals("placeholder2")){
                wasCalledWithParser2 = true;
            }
            if(pc.getParsers().get(1).getPlaceholderT().equals("placeholder1")){
                wasCalledWithParser1 = true;
            }
            if(pc.getParsers().get(1).getPlaceholderT().equals("placeholder2")){
                wasCalledWithParser2 = true;
            }
            return new ArrayList<>();
        }
    }

    public class FakedExecutableStepDefinitionSorter2 extends ExecutableStepDefinitionSorter{


        @Override
        public List<ComparableExecutableStepDefinition> sortOnMatches(Collection<ParserCombiner> parserCombiners, String keyExampleText) {


            ExecutableStepDefinition esd1 = new ExecutableStepDefinitionForObject<String>(s -> setString1(s), "I'm set");
            ExecutableStepDefinition esd2 = new ExecutableStepDefinitionForObject<String>(s -> setString2(s), "I'm set too");


            ComparableExecutableStepDefinition oesd1 = new ComparableExecutableStepDefinition(1, esd1);
            ComparableExecutableStepDefinition oesd2 = new ComparableExecutableStepDefinition(2, esd2);

            return Arrays.asList(oesd1, oesd2);

        }
    }
}