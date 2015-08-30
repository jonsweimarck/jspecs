package se.bitbybit.jspecsdemo;

import org.junit.Rule;
import org.junit.Test;
import se.bitbybit.jspecs.KeyExampleExecuter;
import se.bitbybit.jspecs.StepRunner;
import se.bitbybit.jspecs.builders.ExecutableKeyExample;
import se.bitbybit.jspecs.junitspecific.KeyExample;
import se.bitbybit.jspecs.junitspecific.Specification;
import se.bitbybit.jspecs.junitspecific.SpecificationLogger;
import se.bitbybit.jspecs.stepdefinition.StepDefinition;

import java.util.List;

@Specification(
name=" Black-listing senders",
description =
        "> Online harassment is a major issue, and though our product doesn't need to deal with all the scary stuff yet, " +
        "> we need to at least allow people to prevent abusive and unwanted messages coming into their feeds. This is also " +
        "> useful for future analytics, as it may allow us to capture trends and block abuse accounts proactively." +
        ">" +
        "> For now, just ensuring that unwanted messages do not come into feeds is enough." +
        ">" +
        "> We should also ensure that we're not removing messages from the feeds of other users.")

public class BlacklistingSendersTest {
    @Rule
    public SpecificationLogger logger = new SpecificationLogger();


    @KeyExample(description =
            "Marina has blocked the following senders:" +
            " * Joe" +
            " * Tom" +

            "Joe has blocked the following senders:" +
            " * Tom" +

            "When Tom sends a message, " +
            "it will appear in the feeds for:" +
            " * Tom" +

            "When Joe sends a message," +
            "it will appear in the feeds for:" +
            " * Tom" +
            " * Joe" +

            "When Marina sends a message," +
            "it will appear in the feeds for:" +
            " * Tom" +
            " * Joe" +
            " * Marina"
    )
    @Test
    public void dadsad(){
        StepRunner sr = new StepRunner();
        sr.addStepDef(
                "{string} has blocked",
                (StepDefinition.StepDefinitionForObject<String>) blocker -> setBlocker(blocker));

        sr.addStepDef(
                "the following senders: {list<string>}",
                (StepDefinition.StepDefinitionForList<String>) senders -> {setSenders(senders);
        });

        sr.addStepDef(
                "{string} sends a message",
                (StepDefinition.StepDefinitionForObject<String>) sender -> sendMessage(sender));

        sr.runStepsOnSpecExample(logger);

    }

    @Test
    public void dfsdf(){
  /*
        StepRunner2
                .whenFound().then().then()
                .run();

        SpecExample.givenBy(logger).lookingForPattern()

        StepRunner.with("{string} has blocked").triggering(blocker -> setBlocker(blocker))

        SpecExample
                .suppliedBy("dfsdfsfsdf")
                .shouldBeSearchedFor().whichWillTrigger().and().and()
                .

         with().SearchString("{string} has blocked")
                    .triggering()
                    .and()
                .SearchString("{string} has blocked")
                    .triggering()
                .searchinSpecificationExample()
                .now();

        use().searchString()

        when(patte)
*/
        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())

                .addSearchPattern("{blockerName} has blocked the following senders: {senderNames}")
                    .binding("{blockerName}").toStringIn(blockerName -> setBlocker(blockerName))
                    .binding("{senderNames}").toStringListIn(senderNames -> setSenders(senderNames))

                .addSearchPattern("{senderName} sends a message")
                    .binding("{senderName}").toStringIn(senderName -> sendMessage(senderName))

                .run(new KeyExampleExecuter());

    }


    private void setSenders(List<String> senders) {
        System.out.println("setSenders()");
        for(String s : senders){
            System.out.println("'" + s + "'");
        }
    }

    private void setBlocker(String blockerName) {
        System.out.println("setBlocker('"+ blockerName +"')");
    }

    private void sendMessage(String senderNameName) {
        System.out.println("sendMessage('"+ senderNameName +"')");
    }

}
