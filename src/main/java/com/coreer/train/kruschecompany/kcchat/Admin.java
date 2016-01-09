package com.coreer.train.kruschecompany.kcchat;

/**
 * Created by aieremenko on 12/31/15.
 */
public class Admin implements User {
    public Admin(String nickname){}

    @Override
    public void broadcast(String message) {

    }

    @Override
    public void attend(Chat chat) {

    }

    @Override
    public History getGlobalChatHistory() {
        return null;
    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public void broadcast(Chat chat, String message) {

    }

    @Override
    public void handleMessageEvent(Chat chat, Message capture) {

    }
}

