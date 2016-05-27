package com.coreer.train.kruschecompany.kcchat;

import java.util.List;

/**
 * Created by aieremenko on 12/30/15.
 */
public interface User {

    void broadcast(String message);

    Chat attend(final Chat chat);

    History getGlobalChatHistory();

    History getChatHistory(String chatName);

    String getNickname();

    Message broadcast(Chat chat, String message);

    void handleMessageEvent(Chat chat, Message message);

    Chat getChat(String chatName);

    Chat createChat(String chatName, List<User> attandees);
}
