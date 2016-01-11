package com.coreer.train.kruschecompany.kcchat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aieremenko on 12/31/15.
 */
public class Member implements User {

    private String nickname;
    private final ChatRoom chatRoom;
    private final Map<String, Chat> chats = new HashMap<>();


    public Member(String nickname, ChatRoom chatRoom) {
        this.nickname = nickname;
        this.chatRoom = chatRoom;
        final Chat globalChat = chatRoom.getGlobalChat();
        this.chats.put(globalChat.getName(), globalChat);
    }

    @Override
    public void broadcast(String message) {
        chatRoom.getGlobalChat().broadcast(this, message);
    }

    @Override
    public Chat attend(Chat chat) {
        this.chats.put(chat.getName(), chat);
        return chat;
    }

    @Override
    public History getGlobalChatHistory() {
        return chatRoom.getBroadcastingHistory();
    }

    @Override
    public History getChatHistory(String chatName) {
        final Chat chat = chats.get(chatName);
        if (null != chat)
            return chat.getHistory();
        else
            return new NullHistory();
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void broadcast(Chat chat, String message) {
        chat.broadcast(this, message);
    }

    @Override
    public void handleMessageEvent(Chat chat, Message message) {

    }

    @Override
    public Chat getChat(String chatName) {
        return chats.get(chatName);
    }

    @Override
    public Chat createChat(String chatName, List<User> attandees) {
        return chatRoom.createChat(chatName, this, attandees);
    }


}
