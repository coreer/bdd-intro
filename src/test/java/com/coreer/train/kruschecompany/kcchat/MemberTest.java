package com.coreer.train.kruschecompany.kcchat;

import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by aieremenko on 12/30/15.
 */
public class MemberTest {

    @Test
    public void shouldBeAbleToBroadcastToGlobalChat() {
        final ChatRoom chatRoom = mock(ChatRoom.class);
        final Chat globalChat = mock(Chat.class);
        when(chatRoom.getGlobalChat()).thenReturn(globalChat);

        final Member stiff = new Member("Stiff", chatRoom);

        final String message = "message";
        stiff.broadcast(message);

        verify(globalChat).broadcast(stiff, message);
    }

    @Test
    public void shouldChatHistoryContainsTheMessageIfMemeberGetsMessageEvent() {
        final ChatRoom chatRoom = mock(ChatRoom.class);
        final Chat globalChat = mock(Chat.class);
        final History globalChatHistory = mock(History.class);
        when(chatRoom.getGlobalChat()).thenReturn(globalChat);
        when(globalChat.getHistory()).thenReturn(globalChatHistory);

        final Member stiff = new Member("Stiff", chatRoom);
        final Member oleg = new Member("Oleg", chatRoom);

        chatRoom.addUsers(Arrays.asList(new Member[]{stiff, oleg}));

        final Message msg = new Message(oleg, "Hi Stiff");
        stiff.handleMessageEvent(globalChat, msg);


    }
}