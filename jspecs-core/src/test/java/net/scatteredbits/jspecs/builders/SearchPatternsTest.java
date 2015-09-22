package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Iterator;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asInteger;
import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

public class SearchPatternsTest {

    @Test
    public void listIsInitiallyEmpty(){
        assertTrue(new SearchPatterns().getParserCombiners().isEmpty());
    }

    @Test
    public void searchPatternIsNotAddedIfNotBound(){
        SearchPatterns sp = new SearchPatterns().add(new SearchPattern("pattern"));
        assertTrue(sp.getParserCombiners().isEmpty());
    }


    @Test
    public void expectedSearchPatternWithSingleStringBindingCreatesSingleParserCombiner(){
        SearchPatterns sp = new SearchPatterns()
                .add( new SearchPattern("pattern")
                        .binding("placeholder", asString).to(new OneArgMethod<>(System.out::println)));

        assertThat(sp.getParserCombiners().size(), Is.is(1));

        ParserCombiner pm = sp.getParserCombiners().iterator().next();

        assertThat(pm.getParsers(), hasSize(1));
        assertThat(pm.getParsers().get(0).getStringPattern().getOriginalPattern(), Is.is("pattern"));
        assertThat(pm.getParsers().get(0).getPlaceholderT(), Is.is("placeholder"));
    }

    @Test
    public void expectedSearchPatternWithSingleIntegerBindingCreatesSingleParserCombiner(){
        SearchPatterns sp = new SearchPatterns()
                .add( new SearchPattern("pattern")
                        .binding("placeholder", asInteger).to(new OneArgMethod<>(System.out::println)));

        assertThat(sp.getParserCombiners().size(), Is.is(1));

        ParserCombiner pm = sp.getParserCombiners().iterator().next();

        assertThat(pm.getParsers(), hasSize(1));
        assertThat(pm.getParsers().get(0).getStringPattern().getOriginalPattern(), Is.is("pattern"));
        assertThat(pm.getParsers().get(0).getPlaceholderT(), Is.is("placeholder"));
    }

    @Test
    public void twoSearchPatternWithSingleBindingGeneratesTwoParserCombinersWithSingleEntry(){
        SearchPatterns sp = new SearchPatterns()
            .add(new SearchPattern("pattern with {placeholder1}")
                .binding("{placeholder1}", asString).to(new OneArgMethod<>(System.out::println)))
            .add(new SearchPattern("pattern with {placeholder2}")
                .binding("{palceholder2}", asString).to(new OneArgMethod<>(System.out::println)));

        assertThat(sp.getParserCombiners().size(), Is.is(2));

        Iterator<ParserCombiner> iter = sp.getParserCombiners().iterator();
        ParserCombiner pm = iter.next();
        assertThat(pm.getParsers(), hasSize(1));

        pm = iter.next();
        assertThat(pm.getParsers(), hasSize(1));
    }

    @Test
    public void searchPatternWithTwoBindingsEachToOneMethodGeneratesOneParserCombinerWithTwoEntries(){
        SearchPatterns sp = new SearchPatterns()
            .add(new SearchPattern("pattern with both {placeholder1} and {placeholder2}")
                .binding("{placeholder1}", asString).to(new OneArgMethod<>(System.out::println))
                .binding("{placeholder2}", asString).to(new OneArgMethod<>(System.out::println)));

        assertThat(sp.getParserCombiners().size(), Is.is(1));

        ParserCombiner pm = sp.getParserCombiners().iterator().next();

        assertThat(pm.getParsers(), hasSize(2));
    }

    @Test
    public void searchPatternWithTwoBindingsToSingleMethodGeneratesParserCombinerWithSingleParser(){
        SearchPatterns sp = new SearchPatterns()
                .add(new SearchPattern("pattern with both {placeholder1} and {placeholder2}")
                        .binding("{placeholder1}", asString)
                        .binding("{placeholder2}", asString)
                            .to(new TwoArgMethod<>((arg1, arg2) -> System.out.println("" + arg1 + arg2))));

        assertThat(sp.getParserCombiners().size(), Is.is(1));

        ParserCombiner pm = sp.getParserCombiners().iterator().next();

        assertThat(pm.getParsers(), hasSize(1));
    }

}