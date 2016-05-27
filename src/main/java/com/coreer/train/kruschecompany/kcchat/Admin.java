package com.coreer.train.kruschecompany.kcchat;

import java.util.List;

/**
 * Created by aieremenko on 12/31/15.
 */
public class Admin implements User {
    public Admin(String nickname){}

    @Override
    public void broadcast(String message) {

    }

    @Override
    public Chat attend(Chat chat) {
        return chat;
    }

    @Override
    public ChatHistory getGlobalChatHistory() {
        return null;
    }

    @Override
    public History getChatHistory(String chatName) {
        return null;
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public Message broadcast(Chat chat, String message) {
        return null;
    }

    @Override
    public void handleMessageEvent(Chat chat, Message capture) {

    }

    @Override
    public Chat getChat(String chatName) {
        return null;
    }

    @Override
    public Chat createChat(String chatName, List<User> attandees) {
        return null;
    }
}

