package net.scatteredbits.jspecs.builders;

import org.junit.Test;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PatternContainerTest {

    @Test
    public void shouldCreateAsExpectedWhenArgumentTypeIsString(){
        PatternContainer pc = new PatternContainer(asString, "{string}", "A text with a {string}", "GROUPNAME");

        assertThat(pc.getRegexpPatternWithGroupName(), is("A text with a (?<GROUPNAME>\\w+)"));
        assertThat(pc.getRegexpPatternWithoutGroupName(), is("A text with a (\\w+)"));
        assertThat(pc.getOriginalPattern(), is("A text with a {string}"));
        assertThat(pc.getCompiledRegexpPattern().pattern(), is(pc.getRegexpPatternWithGroupName()));
    }

    @Test
    public void shouldCreateAsExpectedWhenArgumentTypeIsInteger(){
        PatternContainer pc = new PatternContainer(asInteger, "{integer}", "A text with an {integer}", "GROUPNAME");

        assertThat(pc.getRegexpPatternWithGroupName(), is("A text with an (?<GROUPNAME>\\w+)"));
        assertThat(pc.getRegexpPatternWithoutGroupName(), is("A text with an (\\w+)"));
        assertThat(pc.getOriginalPattern(), is("A text with an {integer}"));
        assertThat(pc.getCompiledRegexpPattern().pattern(), is(pc.getRegexpPatternWithGroupName()));
    }

    @Test
    public void shouldCreateAsExpectedWhenArgumentTypeIsStringList(){
        PatternContainer pc = new PatternContainer(asStringList, "{list}", "A text with a {list}", "GROUPNAME");

        assertThat(pc.getRegexpPatternWithGroupName(), is("A text with a (?<GROUPNAME>\\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+)"));
        assertThat(pc.getRegexpPatternWithoutGroupName(), is("A text with a (\\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+)"));
        assertThat(pc.getOriginalPattern(), is("A text with a {list}"));
        assertThat(pc.getCompiledRegexpPattern().pattern(), is(pc.getRegexpPatternWithGroupName()));
    }

    @Test
    public void shouldCreateAsExpectedWhenArgumentTypeIsIntegerList(){
        PatternContainer pc = new PatternContainer(asIntegerList, "{list}", "A text with a {list}", "GROUPNAME");

        assertThat(pc.getRegexpPatternWithGroupName(), is("A text with a (?<GROUPNAME>\\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+)"));
        assertThat(pc.getRegexpPatternWithoutGroupName(), is("A text with a (\\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+)"));
        assertThat(pc.getOriginalPattern(), is("A text with a {list}"));
        assertThat(pc.getCompiledRegexpPattern().pattern(), is(pc.getRegexpPatternWithGroupName()));
    }
}