package se.bitbybit.jspecs.builders;

import org.junit.Test;
import se.bitbybit.jspecs.KeyExampleExecuter;
import se.bitbybit.jspecs.stepdefinition.ParserCombiner;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


public class ExecutableKeyExampleTest {


    @Test
    public void should_get_empty_map_on_run_if_no_searchPatterns_are_set(){
        FakeKeyExampleExecuter fakeExecuter = new FakeKeyExampleExecuter();

        ExecutableKeyExample
                .forKeyExample("Bla bla bla")
                .run(fakeExecuter);

        assertThat(fakeExecuter.parserCombiners.size(), is(0));
    }

    @Test
    public void should_get_parserCombiners_and_keyExampleText_on_run(){

        FakeKeyExampleExecuter fakeExecuter = new FakeKeyExampleExecuter();
        Collection<ParserCombiner> expectedParserCombiners = new HashSet<>();

        SearchPatterns mockedSearchPatterns = given(mock(SearchPatterns.class).getParserCombiners()).willReturn(expectedParserCombiners).getMock();

        ExecutableKeyExample
                .forKeyExample("should find plopp as a single string")
                .withSearchPatterns(mockedSearchPatterns)
                .run(fakeExecuter);

        assertThat(fakeExecuter.parserCombiners, is(expectedParserCombiners));
    }



    public class FakeKeyExampleExecuter implements KeyExampleExecuter{

        public Collection<ParserCombiner> parserCombiners;

        @Override
        public void execute(String keyExampleText, Collection<ParserCombiner> parserCombiners) {
            this.parserCombiners = parserCombiners;
        }
    }
}