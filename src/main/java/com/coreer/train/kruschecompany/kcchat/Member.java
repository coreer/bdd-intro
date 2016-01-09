package com.coreer.train.kruschecompany.kcchat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aieremenko on 12/31/15.
 */
public class Member implements User {

    private String nickname;
    private final ChatRoom chatRoom;
    private final List<Chat> chats = new ArrayList<>();


    public Member(String nickname, ChatRoom chatRoom) {
        this.nickname = nickname;
        this.chatRoom = chatRoom;
        this.chats.add(chatRoom.getGlobalChat());
    }

    @Override
    public void broadcast(String message) {
        chatRoom.getGlobalChat().broadcast(this, message);
    }

    @Override
    public void attend(Chat chat) {
        this.chats.add(chat);
    }

    @Override
    public History getGlobalChatHistory() {
        return chatRoom.getBroadcastingHistory();
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void broadcast(Chat chat, String message) {}

    @Override
    public void handleMessageEvent(Chat chat, Message message) {

    }


}
