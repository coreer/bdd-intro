package com.coreer.train.kruschecompany.kcchat;

/**
 * Created by aieremenko on 12/30/15.
 */
public interface User {

    void broadcast(String message);

    void attend(final Chat chat);

    History getGlobalChatHistory();

    String getNickname();

    void broadcast(Chat chat, String message);

    void handleMessageEvent(Chat chat, Message message);
}
