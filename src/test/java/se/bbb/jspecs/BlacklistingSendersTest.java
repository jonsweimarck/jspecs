package se.bbb.jspecs;

import org.junit.Rule;
import org.junit.Test;

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

    @CurrentKeyExampleDescription
    public String currentDescription;


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
        SpecificationHelper sh = new SpecificationHelper<BlacklistingSendersTest>(this);
        StepRunner sr = new StepRunner();
        sr.addStepDef(
                "(.*) has blocked",
                (StepDefinition.StepDefinitionForObject<String>) blocker -> setBlocker(blocker));

        sr.addStepDef(
                "the following senders: \\* ([A-Z])\\w+",
                (StepDefinition.StepDefinitionForList<String>) senders -> {setSenders(senders);
        });

        sr.addStepDef(
                "(.*) sends a message",
                (StepDefinition.StepDefinitionForObject<String>) sender -> sendMessage(sender));

        sr.runStepsOnSpecExample(sh);

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
