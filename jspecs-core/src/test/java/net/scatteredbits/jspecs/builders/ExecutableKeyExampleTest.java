package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.KeyExampleExecuter;
import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


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

    @Test
    public void should_use_tableSearchConverter(){

        FakeKeyExampleExecuter fakeExecuter = new FakeKeyExampleExecuter();
        Collection<TableSearch> expectedTableSearches = new HashSet<>();

        SearchPatterns mockedSearchPatterns = given(mock(SearchPatterns.class).getTableSearches()).willReturn(expectedTableSearches).getMock();

        TableSearchConverter converterSpy = mock(TableSearchConverter.class);

        ExecutableKeyExample
                .forKeyExample("key example text")
                .withTableSearchConverter(converterSpy)                 // <-- only for tests
                .withSearchPatterns(mockedSearchPatterns)
                .run(fakeExecuter);

        ArgumentCaptor<Collection> argument = ArgumentCaptor.forClass(Collection.class);
        verify(converterSpy).convert(eq("key example text"), argument.capture());
        assertThat(argument.getValue(), is(expectedTableSearches));
    }



    public class FakeKeyExampleExecuter implements KeyExampleExecuter{

        public Collection<ParserCombiner> parserCombiners;

        @Override
        public void execute(String keyExampleText, Collection<ParserCombiner> parserCombiners) {
            this.parserCombiners = parserCombiners;
        }
    }
}