package net.scatteredbits.jspecsdemo;

import net.scatteredbits.jspecs.DefaultKeyExampleExecuter;
import net.scatteredbits.jspecs.ExecutableStepDefinitionSorter;
import net.scatteredbits.jspecs.builders.*;
import net.scatteredbits.jspecs.junitspecific.KeyExample;
import net.scatteredbits.jspecs.junitspecific.Specification;
import net.scatteredbits.jspecs.junitspecific.SpecificationLogger;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asStringList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * This is the same specification and key example that is used in DaSpec's example at
 * http://daspec.com/examples/basic/checking_for_missing_and_additional_list_items/
 *
 * Implemented here for easy comparinson between the two framework's style
 */

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


    // The not so advanced component/service we want to test
    private FeedService feedService = new FeedService();

    private Integer lastMessageId;


    @KeyExample(description =
            "Marina has blocked the following senders: " +
            "* Joe " +
            "* Tom " +

            "Joe has blocked the following senders: " +
            "* Tom " +

            "When Tom sends a message, " +
            "it will appear in the feeds for: " +
            "* Tom " +

            "When Joe sends a message," +
            "it will appear in the feeds for: " +
            "* Tom " +
            "* Joe " +

            "When Marina sends a message," +
            "it will appear in the feeds for: " +
            "* Tom " +
            "* Joe " +
            "* Marina "
    )
    @Test
    public void blacklistingSenders(){

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())

                .withSearchPatterns(new SearchPatterns()
                    .add(
                            new SearchPattern("{blockerName} has blocked the following senders: {senderNames}")
                                .binding("{blockerName}", asString)
                                .binding("{senderNames}", asStringList)
                                .to(new TwoArgMethod<>(this::setBlockedSenders))
                    )
                    .add(
                            new SearchPattern("{senderName} sends a message")
                            .binding("{senderName}", asString)
                            .to(new OneArgMethod<>(this::sendMessage))
                    )
                    .add(
                            new SearchPattern("in the feeds for: {receipients}")
                            .binding("{receipients}", asStringList)
                            .to(new OneArgMethod<>(this::assertExpectedReceipients))
                ))
                .run(defaultKeyExampleExecuter);
    }

    private void setBlockedSenders(String user, List<String> blockedList) {
        feedService.addFeed(user);
        blockedList.forEach(blocked -> {
            feedService.addFeed(blocked);
            feedService.blockSender(user, blocked);
        });
    }

    private void sendMessage(String sender) {
        lastMessageId = feedService.send(sender, "Some text");
    }

    private void assertExpectedReceipients(List<String> expectedFeedsList) {
        assertThat(feedService.getFeedsForMessageId(lastMessageId), containsInAnyOrder(expectedFeedsList.toArray()));
    }
}
