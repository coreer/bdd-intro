package com.coreer.train.kruschecompany.kcchat;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.*;

/**
 * Created by aieremenko on 12/30/15.
 */
public class ChatRoomTest {

    public static final String USER1_NAME = "Skywalker";
    public static final String USER2_NAME = "ObiWan";
    public static final String USER3_NAME = "Yoda";
    public static final String USER4_NAME = "Dart";
    public static final String USER5_NAME = "Sirius";
    public static User skywalker;
    public static User obiWan;
    public static User yoda;
    public static User dart;
    public static User sirius;


    @BeforeClass
    public static void setUp() {
        skywalker = mock(User.class);
        obiWan = mock(User.class);
        yoda = mock(User.class);
        dart = mock(User.class);
        sirius = mock(User.class);
        when(skywalker.getNickname()).thenReturn(USER1_NAME);
        when(obiWan.getNickname()).thenReturn(USER2_NAME);
        when(yoda.getNickname()).thenReturn(USER3_NAME);
        when(dart.getNickname()).thenReturn(USER4_NAME);
        when(sirius.getNickname()).thenReturn(USER5_NAME);
    }

    /**
     * - create ChatRoom from only admin of it
     * - when we add users to ChatRoom make sure that we don't need anything additional to do with users!
     */

    @Test
    public void shouldCreateChatRoomOnlyFromAdmin() {
        final User admin = mock(User.class);

        final ChatRoom chatRoom = new ChatRoom(admin);

        assertThat(chatRoom.getAdmin(), is(admin));
    }

    @Test
    public void shouldSetAndGetAListOfUsers() {
        final User admin = mock(User.class);
        final List<User> users = firstGroupOfUsers();
        final ChatRoom chatRoom = new ChatRoom(admin, users);

        assertThat(chatRoom.getUsers(), equalTo(users));
    }

    private List<User> firstGroupOfUsers() {
        final List<User> users = Arrays.asList(new User[]{skywalker, obiWan, yoda});
        return users;
    }

    private List<User> secondGroupOfUsers() {
        final List<User> users = Arrays.asList(new User[]{dart, sirius});
        return users;
    }

    @Test
    public void shouldAddAdditionalNewUsersWhichAreMergedWithExistedOnes() {
        final User admin = mock(User.class);
        final List<User> light = firstGroupOfUsers();
        final ChatRoom chatRoom = new ChatRoom(admin, light);

        final List<User> dark = secondGroupOfUsers();
        chatRoom.addUsers(dark);

        final Comparator<User> userComparator = (u1, u2) -> u1.hashCode() - u2.hashCode();

        final List<User> mergedExpectedResult = Stream.concat(light.stream(), dark.stream()).sorted(userComparator).collect(Collectors.toList());

        final List<User> chatUsers = chatRoom.getUsers().stream().sorted(userComparator).collect(Collectors.toList());

        assertThat(chatUsers, equalTo(mergedExpectedResult));
    }

    @Test
    public void shouldProvidePossibilityToCreateAChat() {
        final Chat justCreatedChat = buildChat();

        assertThat(justCreatedChat, not(nullValue()));
    }

    private Chat buildChat() {
        final User admin = mock(User.class);
        final List<User> users = firstGroupOfUsers();
        final ChatRoom chatRoom = new ChatRoom(admin, users);

        return chatRoom.createChat("chatName", admin, users);
    }

    @Test
    public void shouldBeChatProvidedWithHistoryByItsBuilder() {
        final Chat justCreatedChat = buildChat();

        assertThat(justCreatedChat.getHistory(), not(nullValue()));
    }

    @Test
    public void shouldProvideAccessToChatByItsName() {
        final ChatRoom chatRoom = new ChatRoom(mock(User.class), firstGroupOfUsers());
        final String chatName = "myChat";
        final Chat justCreatedChat = chatRoom.createChat(chatName, mock(User.class));

        final Chat chatByName = chatRoom.getChat(chatName);

        assertThat(chatByName, is(justCreatedChat));
    }

    @Test
    public void shouldDefaultChatBeBuildByDefault() {
        final ChatRoom chatRoom = new ChatRoom(mock(User.class), firstGroupOfUsers());

        final Chat globalChat = chatRoom.getGlobalChat();

        assertThat(globalChat, not(nullValue()));
    }

    @Test
    public void shouldDefaultChatBeProvidedWithHistoryAsWell() {
        final ChatRoom chatRoom = new ChatRoom(mock(User.class), firstGroupOfUsers());

        final Chat globalChat = chatRoom.getGlobalChat();

        assertThat(globalChat.getHistory(), not(nullValue()));
    }

    @Test
    public void should_make_creatorAndAttandees_toAttendIn_createdChat() {
        final List<User> firstGroupOfUsers = firstGroupOfUsers();
        final ChatRoom chatRoom = new ChatRoom(mock(User.class), firstGroupOfUsers);
        final List<User> secondGroupOfUsers = secondGroupOfUsers();
        chatRoom.addUsers(secondGroupOfUsers);
        User chatCreator = mock(User.class);

        final Chat chat = chatRoom.createChat("chat name", chatCreator, secondGroupOfUsers);

        verify(chatCreator).attend(chat);
        for(User user : secondGroupOfUsers) {
            verify(user).attend(chat);
        }
        for(User user : firstGroupOfUsers) {
            verify(user, never()).attend(chat);
        }
    }

    @Test
    public void should_find_user_by_name() {
        final List<User> firstGroupOfUsers = firstGroupOfUsers();

        final ChatRoom chatRoom = new ChatRoom(mock(User.class), firstGroupOfUsers);

        final User isUser2 = chatRoom.getUser(USER2_NAME);

        assertThat(isUser2, is(obiWan));
    }

}