package com.coreer.train.kruschecompany.kcchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aieremenko on 12/30/15.
 */
public class ChatRoom {
    public static final String GLOBAL_CHAT_NAME = "General Chat";

    private User admin;
    private List<User> users = new ArrayList<>();
    private final Chat globalChat;
    private final Map<String, Chat> chats = new HashMap<>();

    public ChatRoom(User admin, List<User> users) {
        this.admin = admin;
        addUsers(users);
        this.globalChat = createChat(GLOBAL_CHAT_NAME, admin);
    }


    public ChatRoom(User admin) {
        this(admin, new ArrayList<>());
    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }

    public List<User> getUsers() {
        return users;
    }

    public Chat getGlobalChat() {
        return globalChat;
    }

    public ChatHistory getBroadcastingHistory() {
        return globalChat.getHistory();
    }

    public Chat getChat(String chatName) {
        return chats.get(chatName);
    }

    public Chat createChat(String chatName, User creator, List<User> attendees) {
        final Chat chat = new Chat(chatName, creator, attendees);
        chat.setHistory(new ChatHistory(chat));
        chats.put(chatName, chat);

        for(User attendee : chat.getAttendees()) {
            attendee.attend(chat);
        }

        return chat;
    }

    public Chat createChat(String chatName, User creator) {
       return createChat(chatName, creator, getUsers());
    }

    public User getAdmin() {
        return admin;
    }
}
