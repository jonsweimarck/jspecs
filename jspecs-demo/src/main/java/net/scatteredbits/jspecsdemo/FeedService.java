package net.scatteredbits.jspecsdemo;


import java.util.*;
import java.util.stream.Collectors;

public class FeedService {

    Set<String> feeds = new HashSet<>();
    Map<String, List<String>> blacklists = new HashMap<>();
    List<Posting> messages = new ArrayList<>();


    public void addFeed(String user) {
        if(! feeds.contains(user)){
            feeds.add(user);
        }
    }


    public void blockSender(String user, String blocked) {
        if(! blacklists.containsKey(user)){
            blacklists.put(user, new ArrayList<>());
        }
        blacklists.get(user).add(blocked);
    }

    public Integer send(String sender, String message) {
        messages.add(new Posting(message, usersNotBlacklistedSender(sender)));
        return messages.size() -1;
    }

    private Set<String> usersNotBlacklistedSender(String sender) {
        return feeds.stream()
                .filter(user -> blacklists.get(user) == null || ! blacklists.get(user).contains(sender))
                .collect(Collectors.toSet());
    }

    public Set<String> getFeedsForMessageId(Integer lastMessageId) {
        return messages.get(lastMessageId).destinations;
    }

    private class Posting {
        public String message;
        public Set<String> destinations;

        public Posting(String message, Set<String> destinations) {
            this.message = message;
            this.destinations = destinations;
        }
    }
}
