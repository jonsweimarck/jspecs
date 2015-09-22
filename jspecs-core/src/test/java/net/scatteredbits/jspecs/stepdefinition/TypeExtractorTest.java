package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.SearchPatterns;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class TypeExtractorTest {

    @Test
    public void stringsShouldBeExtracted() {
        TypeExtractor<String> te = new TypeExtractor<>();

        String extracted = te.extractArg(SearchPatterns.PlaceHolderType.asString, "AllThisShouldBeExtracted");

        assertThat(extracted, is("AllThisShouldBeExtracted"));
    }

    @Test
    public void integersShouldBeExtracted() {
        TypeExtractor<Integer> te = new TypeExtractor<>();

        Integer extracted = te.extractArg(SearchPatterns.PlaceHolderType.asInteger, "99");

        assertThat(extracted, is(99));
    }

    @Test
    public void stringListsShouldBeExtracted() {
        TypeExtractor<List<String>> te = new TypeExtractor<>();

        List<String> extracted = te.extractArg(SearchPatterns.PlaceHolderType.asStringList, "* Bob * Karen");

        assertThat(extracted, hasSize(2));
        assertThat(extracted.get(0), is("Bob"));
        assertThat(extracted.get(1), is("Karen"));
    }

    @Test
    public void integerListsShouldBeExtracted() {
        TypeExtractor<List<Integer>> te = new TypeExtractor<>();

        List<Integer> extracted = te.extractArg(SearchPatterns.PlaceHolderType.asIntegerList, "* 42 * 43");

        assertThat(extracted, hasSize(2));
        assertThat(extracted.get(0), is(42));
        assertThat(extracted.get(1), is(43));
    }

    @Test(expected = ClassCastException.class)
    public void typeMismatchBetweenIntegerAndStringShouldThrow() {
        TypeExtractor<String> te = new TypeExtractor<>();

        String extracted = te.extractArg(SearchPatterns.PlaceHolderType.asInteger, "99"); // <-- asInteger

        assertThat(extracted, is("99"));
    }
}