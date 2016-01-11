package com.coreer.train.kruschecompany.kcchat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ChatTest {

    @Captor ArgumentCaptor<Message> messageCaptor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldHaveName() {
        User creator = mock(User.class);
        User attendee1 = mock(User.class);
        User attendee2 = mock(User.class);
        final String chatName = "My chat name!";

        final Chat chat = new Chat(chatName, creator, Arrays.asList(new User[]{attendee1, attendee2}));

        assertThat(chat.getName(), is(chatName));
    }

    @Test
    public void shouldBroadcastMessageFromUserToAll() {
        User creator = mock(User.class);
        User attendee1 = mock(User.class);
        User attendee2 = mock(User.class);

        final Chat chat = new Chat("My chat", creator, Arrays.asList(new User[]{attendee1, attendee2}));
        final ChatHistory history = mock(ChatHistory.class);
        chat.setHistory(history);

        final String msg = "Message from!";
        chat.broadcast(attendee1, msg);


        verify(creator).handleMessageEvent(eq(chat), messageCaptor.capture());
        final Message creatorMsg = messageCaptor.getValue();
        assertThat(creatorMsg.getContent(), is(msg));
        assertThat(creatorMsg.getAuthor(), is(attendee1));

        verify(attendee1).handleMessageEvent(eq(chat), messageCaptor.capture());
        assertThat(messageCaptor.getValue(), is(creatorMsg));

        verify(attendee2).handleMessageEvent(eq(chat), messageCaptor.capture());
        assertThat(messageCaptor.getValue(), is(creatorMsg));
    }

    @Test
    public void shouldAnyMessageBePlacedInChatHistory() {
        User creator = mock(User.class);
        User attendee1 = mock(User.class);
        User attendee2 = mock(User.class);

        final Chat chat = new Chat("My chat", creator, Arrays.asList(new User[]{attendee1, attendee2}));
        final ChatHistory history = mock(ChatHistory.class);
        chat.setHistory(history);

        final String msg = "Message from!";
        chat.broadcast(attendee1, msg);

        verify(history).add(messageCaptor.capture());
        final Message msgObj = messageCaptor.getValue();
        assertThat(msgObj.getContent(), equalTo(msg));

    }
}