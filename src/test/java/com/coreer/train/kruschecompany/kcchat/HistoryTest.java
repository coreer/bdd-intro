package com.coreer.train.kruschecompany.kcchat;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;

/**
 * Created by aieremenko on 12/31/15.
 */
public class HistoryTest {

    History history;
    Message msg1;
    Message msg2;
    Message msg3;
    Message msg4;


    @Before
    public void init() {
        final Chat chat = mock(Chat.class);
        history = new History(chat);

        final User creator = mock(User.class);
        final User skywokker = mock(User.class);
        final User obione = mock(User.class);
        final User yoda = mock(User.class);

        msg1 = new Message(creator, "Hi from Creator");
        msg2 = new Message(yoda, "Hi from Yoda!");
        msg3 = new Message(skywokker, "Hi from Skywokker!");
        msg4 = new Message(obione, "Hi from Obione!");

        history.add(msg1);
        history.add(msg2);
        history.add(msg3);
        history.add(msg4);
    }

    @Test
    public void shouldBeAbleToIterateThroughAllHistory() {

        List<Message> historyItems = history.fromLastToFirst();

        assertThat(historyItems.size(), is(4));
    }

    @Test
    public void shouldProvidePossibilityToIterateThroughHistoryFromLastToFirst() {

        List<Message> historyItems = history.fromLastToFirst();


        assertThat(historyItems.get(0), equalTo(msg4));
        assertThat(historyItems.get(1), equalTo(msg3));
        assertThat(historyItems.get(2), equalTo(msg2));
        assertThat(historyItems.get(3), equalTo(msg1));
    }

    @Test
    public void shouldProtectHistoryFromModification() {

        List<Message> historyItems = history.fromLastToFirst();



        assertThat(historyItems.get(0), equalTo(msg4));
        assertThat(historyItems.get(0), not(sameInstance(msg4)));

        assertThat(historyItems.get(1), equalTo(msg3));
        assertThat(historyItems.get(1), not(sameInstance(msg3)));

        assertThat(historyItems.get(2), equalTo(msg2));
        assertThat(historyItems.get(2), not(sameInstance(msg2)));

        assertThat(historyItems.get(3), equalTo(msg1));
        assertThat(historyItems.get(3), not(sameInstance(msg1)));
    }
}