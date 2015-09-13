package se.bitbybit.jspecs;

import org.junit.Test;
import se.bitbybit.jspecs.builders.PatternContainer;
import se.bitbybit.jspecs.stepdefinition.ExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionParserForStringList;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StepDefinitionParserForStringListTest {

    private List<String> list;

    @Test
    public void patternContainerShouldContainPatternFromConstructor(){
        StepDefinitionParserForStringList cut = new StepDefinitionParserForStringList(
                "a list: {list<string>}",
                "{list<string>}",
                null);

        PatternContainer patternContainer = cut.getStringPattern();

        assertThat(patternContainer.getOriginalPattern(), is("a list: {list<string>}"));
        assertThat(patternContainer.getCompiledRegexpPattern().pattern(), is("a list: \\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+"));
    }

    @Test
    public void shouldParseOkWhenListAtEnd() {

        StepDefinitionParserForStringList cut = new StepDefinitionParserForStringList(
                "a list: {list<string>}",
                "{list<string>}",
                (StepDefinition.StepDefinitionForList<String>) arg -> setList(arg)
        );

        ExecutableStepDefinition executable = cut.parse(
                "a list: " +
                "* Tommy " +
                "* Annika");
        executable.execute();
        assertThat(list, hasSize(2));
        assertThat(list.get(0), is("Tommy"));
        assertThat(list.get(1), is("Annika"));
    }

    @Test
    public void shouldCreateWorkingRegexpWithListInBeginning() {

        StepDefinitionParserForStringList cut = new StepDefinitionParserForStringList(
                "a list: {list<string>}",
                "{list<string>}",
                (StepDefinition.StepDefinitionForList<String>) arg -> setList(arg)
        );

        ExecutableStepDefinition executable = cut.parse("a list: * Tommy * Annika");
        executable.execute();
        assertThat(list, hasSize(2));
        assertThat(list.get(0), is("Tommy"));
        assertThat(list.get(1), is("Annika"));

    }

    public void setList(List<String> list) {
        this.list = list;
    }
}