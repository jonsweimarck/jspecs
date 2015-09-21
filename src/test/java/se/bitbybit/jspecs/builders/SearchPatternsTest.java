package se.bitbybit.jspecs.builders;

import org.junit.Test;
import se.bitbybit.jspecs.stepdefinition.ParserCombiner;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class SearchPatternsTest {

    @Test
    public void listIsInitiallyEmpty(){
        assertTrue(new SearchPatterns().getParserCombiners().isEmpty());
    }

    @Test
    public void searchPatternIsNotAddedToListIfNotBound(){
        SearchPatterns sp = new SearchPatterns();
        sp.addSearchPattern("pattern");
        assertTrue(sp.getParserCombiners().isEmpty());
    }

    @Test
    public void searchPatternIsNotAddedToListIfNoStepDef(){
        SearchPatterns sp = new SearchPatterns();
        sp.addSearchPattern("pattern").binding("placeholder");
        assertTrue(sp.getParserCombiners().isEmpty());
    }

    @Test
    public void expectedSearchPatternIsAddedToListIfBoundToStepDef(){
        SearchPatterns sp = new SearchPatterns();
        sp.addSearchPattern("pattern")
                .binding("placeholder")
                .toStringIn(placeholder -> System.out.println(placeholder));
        assertThat(sp.getParserCombiners().size(), is(1));

        ParserCombiner pm = sp.getParserCombiners().iterator().next();

        assertThat(pm.getParsers(), hasSize(1));
        assertThat(pm.getParsers().get(0).getStringPattern().getOriginalPattern(), is("pattern"));
        assertThat(pm.getParsers().get(0).getPlaceholder(), is("placeholder"));
    }

    @Test
    public void twoSearchPatternWithSingleBindingGeneratesTwoParserCombinersWithSingleEntry(){
        SearchPatterns sp = new SearchPatterns();
        sp.addSearchPattern("pattern will {palceholder1}")
                .binding("{palceholder1}").toStringIn(placeholder1 -> System.out.println(placeholder1));
        sp.addSearchPattern("pattern will {palceholder2}")
                .binding("{palceholder2}").toStringIn(placeholder2 -> System.out.println(placeholder2));

        assertThat(sp.getParserCombiners().size(), is(2));

        Iterator<ParserCombiner> iter = sp.getParserCombiners().iterator();
        ParserCombiner pm = iter.next();
        assertThat(pm.getParsers(), hasSize(1));

        pm = iter.next();
        assertThat(pm.getParsers(), hasSize(1));
    }

    @Test
    public void searchPatternWithTwoBindingsGeneratesOneParserCombinerWithTwoEntries(){
        SearchPatterns sp = new SearchPatterns();
        sp.addSearchPattern("pattern will both {palceholder1} and {placeholder2}")
                .binding("{palceholder1}").toStringIn(placeholder1 -> System.out.println(placeholder1))
                .binding("{palceholder2}").toStringIn(placeholder2 -> System.out.println(placeholder2));

        assertThat(sp.getParserCombiners().size(), is(1));

        ParserCombiner pm = sp.getParserCombiners().iterator().next();

        assertThat(pm.getParsers(), hasSize(2));
    }

}