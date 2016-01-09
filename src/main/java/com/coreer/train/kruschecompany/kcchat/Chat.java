package com.coreer.train.kruschecompany.kcchat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aieremenko on 12/31/15.
 */
//@todo Builder of this class instances should always provide History instance for each of these
public class Chat {
    private String name;
    private final User creator;
    private final List<User> attendees = new ArrayList<>();
    private History history;


    public Chat(final String name, final User creator, final List<User> attendees) {
        this.name = name;
        this.creator = creator;
        this.attendees.add(creator);
        this.attendees.addAll(attendees);
    }

    public void broadcast(User sender, String message) {
        final Message msg = new Message(sender, message);
        this.history.add(msg);
        for(User user : attendees) {
            user.handleMessageEvent(this, msg);
        }
    }

    public User getCreator() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
