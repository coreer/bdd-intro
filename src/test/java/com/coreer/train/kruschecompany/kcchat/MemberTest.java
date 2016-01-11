package com.coreer.train.kruschecompany.kcchat;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
    public void chatHistory_shouldContain_theMessage_ifMemberGetsMessageEvent() {
        final ChatRoom chatRoom = mock(ChatRoom.class);
        final Chat globalChat = mock(Chat.class);
        final ChatHistory globalChatHistory = mock(ChatHistory.class);
        when(chatRoom.getGlobalChat()).thenReturn(globalChat);
        when(globalChat.getHistory()).thenReturn(globalChatHistory);

        final Member stiff = new Member("Stiff", chatRoom);
        final Member oleg = new Member("Oleg", chatRoom);

        chatRoom.addUsers(Arrays.asList(new Member[]{stiff, oleg}));

        final Message msg = new Message(oleg, "Hi Stiff");
        stiff.handleMessageEvent(globalChat, msg);

    }


    @Test
    public void should_have_interface_for_chatCreation() {
        final ChatRoom chatRoom = mock(ChatRoom.class);
        final Chat globalChat = mock(Chat.class);

        when(chatRoom.getGlobalChat()).thenReturn(globalChat);
        when(globalChat.getName()).thenReturn("Global chat");


        final Member stiff = new Member("Stiff", chatRoom);

        final String chatName = "my chat";
        final List<User> attandeesOfStiffChat = Arrays.asList(new User[]{});
        stiff.createChat(chatName, attandeesOfStiffChat);

        verify(chatRoom).createChat(eq(chatName), eq(stiff), eq(attandeesOfStiffChat));

    }

    @Test
    public void should_invoked_chat_on_broadcast() {
        final ChatRoom chatRoom = mock(ChatRoom.class);
        final Chat globalChat = mock(Chat.class);
        final Chat createdByUserChat = mock(Chat.class);

        when(chatRoom.getGlobalChat()).thenReturn(globalChat);
        when(globalChat.getName()).thenReturn("Global chat");


        final Member stiff = new Member("Stiff", chatRoom);


        final String msg = "Message";
        stiff.broadcast(createdByUserChat, msg);
        verify(createdByUserChat).broadcast(eq(stiff), eq(msg));

    }

}