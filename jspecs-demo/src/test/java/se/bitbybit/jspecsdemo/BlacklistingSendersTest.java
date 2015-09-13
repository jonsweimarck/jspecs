package se.bitbybit.jspecsdemo;

import org.junit.Rule;
import org.junit.Test;
import se.bitbybit.jspecs.DefaultKeyExampleExecuter;
import se.bitbybit.jspecs.ExecutableStepDefinitionSorter;
import se.bitbybit.jspecs.builders.ExecutableKeyExample;
import se.bitbybit.jspecs.builders.SearchPatterns;
import se.bitbybit.jspecs.junitspecific.KeyExample;
import se.bitbybit.jspecs.junitspecific.Specification;
import se.bitbybit.jspecs.junitspecific.SpecificationLogger;

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

    private DefaultKeyExampleExecuter defaultKeyExampleExecuter = new DefaultKeyExampleExecuter(new ExecutableStepDefinitionSorter());


    /**
     * The whole test can be defined and run in a single block of code ...
     */
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
    public void blacklistingSenders_I(){

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())

                .withSearchPatterns(new SearchPatterns()
                    .addSearchPattern("{blockerName} has blocked the following senders: {senderNames}")
                        .binding("{blockerName}").toStringIn(blockerName -> setBlocker(blockerName))
                        .binding("{senderNames}").toStringListIn(senderNames -> setSenders(senderNames))

                    .addSearchPattern("{senderName} sends a message")
                        .binding("{senderName}").toStringIn(senderName -> sendMessage(senderName))
                )

                .run(defaultKeyExampleExecuter);

    }

    /**
     * ... or the SearchPatterns might be defined on its own,
     * for example in the @Before method, to allow for reuse
     * in many tests.
     */
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
    public void blacklistingSenders_II() {

        SearchPatterns searchPatterns = new SearchPatterns()
                .addSearchPattern("{blockerName} has blocked the following senders: {senderNames}")
                    .binding("{blockerName}").toStringIn(blockerName -> setBlocker(blockerName))
                    .binding("{senderNames}").toStringListIn(senderNames -> setSenders(senderNames))

                .addSearchPattern("{senderName} sends a message")
                    .binding("{senderName}").toStringIn(senderName -> sendMessage(senderName)
        );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(searchPatterns)
                .run(defaultKeyExampleExecuter);

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
