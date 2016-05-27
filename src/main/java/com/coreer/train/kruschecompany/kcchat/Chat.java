package com.coreer.train.kruschecompany.kcchat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aieremenko on 12/31/15.
 */
//@todo Builder of this class instances should always provide ChatHistory instance for each of these
public class Chat {
    private String name;
    private final User creator;
    private final List<User> attendees = new ArrayList<>();
    private ChatHistory history;


    public Chat(final String name, final User creator, final List<User> attendees) {
        this.name = name;
        this.creator = creator;
        this.attendees.add(creator);
        this.attendees.addAll(attendees);
    }

    public Message broadcast(User sender, String message) {
        final Message msg = new Message(sender, message);
        this.history.add(msg);
        for(User user : attendees) {
            user.handleMessageEvent(this, msg);
        }
        return msg;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void addAttendee(final User adder, final User attendee){
        assert adder == creator;
        attendees.add(attendee);
    }

    public void removeAttendee(final User adder, final User attendee){
        assert adder == creator;
        attendees.remove(attendee);
    }

    public User getCreator() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public ChatHistory getHistory() {
        return history;
    }

    public void setHistory(ChatHistory history) {
        this.history = history;
    }
}
