package se.bitbybit.jspecs.builders;

import org.junit.Test;
import se.bitbybit.jspecs.KeyExampleExecuter;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParser;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class ExecutableKeyExampleTest {


    @Test
    public void testsd(){
        Pattern p1 = Pattern.compile("ab" +"c");
        Pattern p2 = Pattern.compile("abc");

        assertEquals(p1.pattern(), p2.pattern());
    }

    @Test
    public void shouldCreateCorrectMapForSingleSearchPatternWithSingleBinding(){

        FakeKeyExampleExecuter fakeExecuter = new FakeKeyExampleExecuter();

        ExecutableKeyExample
                .forKeyExample("should find plopp as a single string")

                .addSearchPattern("{theString} as a single string")
                .binding("{theString}").toStringIn(theString -> {})
                .run(fakeExecuter);

        assertThat(fakeExecuter.searchString2stepDefParser.size(), is(1));

        Set<Pattern> keys = fakeExecuter.searchString2stepDefParser.keySet();
        Pattern actualP1 = keys.iterator().next();
        List<StepDefinitionRegexpParser> actualValuesForP1 = fakeExecuter.searchString2stepDefParser.get(actualP1);

        assertThat(actualP1.pattern(), is("(.*) as a single string"));
        assertThat(actualValuesForP1, hasSize(1));

        StepDefinitionRegexpParser actualV1forP1 = actualValuesForP1.get(0);
        assertThat(actualV1forP1.patternAsRegexp().pattern(), is(actualP1.pattern()));
    }


    public class FakeKeyExampleExecuter implements KeyExampleExecuter{

        public Map<Pattern, List<StepDefinitionRegexpParser>> searchString2stepDefParser;

        @Override
        public void execute(Map<Pattern, List<StepDefinitionRegexpParser>> searchString2stepDefParser) {
            this.searchString2stepDefParser = searchString2stepDefParser;
        }
    }
}