package se.bitbybit.jspecs.builders;

import org.junit.Test;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PatternContainerTest {

    @Test
    public void equalsComparesBothPatternStrings(){
        PatternContainer pc1 = new PatternContainer("a pattern with a {string}", "asdfg", "");

        PatternContainer pc2 = new PatternContainer("a pattern with a {string}", "drtee", "");
        assertThat(pc1, is(not(pc2)));

        PatternContainer pc3 = new PatternContainer("a pattern with a {STRING}", "asdfg", "");
        assertThat(pc1, is(not(pc3)));

        PatternContainer pc4 = new PatternContainer("a pattern with a {string}", "asdfg", "");
        assertThat(pc1, is(pc4));
    }

}