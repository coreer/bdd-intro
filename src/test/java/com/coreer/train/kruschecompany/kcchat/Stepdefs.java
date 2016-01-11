package com.coreer.train.kruschecompany.kcchat;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Created by aieremenko on 12/30/15.
 */
public class Stepdefs {
    public static final String BROADCAST_MESSAGE = "Hi all!";
    public static final String CHAT_NAME = "someUserChat";
    public static final String CHAT_MSG = "hi my fiends!";

    private ChatRoom chatRoom;

    private User anyUser;
    private User admin = new Admin("admin");

    private User someUser;
    private Chat chatCreatedBySomeUser;
    private List<User> userListOfChatCreatedBySomeUser;


    public static class ChatUser {
        public String nickname;
        public String email;
    }

    @Given("^there are several users added to Company Room:$")
    public void there_are_several_users_added_to_Company_Room(List<ChatUser> users) throws Throwable {
        chatRoom = new ChatRoom(admin);
        final List<User> chatRoomMemebers = users.stream().map(user -> new Member(user.nickname, chatRoom)).collect(Collectors.toList());
        chatRoom.addUsers(chatRoomMemebers);
    }


    @When("^any of them sends some message$")
    public void any_of_them_sends_some_message() throws Throwable {
        anyUser = getRandomUser(chatRoom);
        anyUser.broadcast(BROADCAST_MESSAGE);
    }

    private User getRandomUser(ChatRoom chatRoom) {
        List<User> users = chatRoom.getUsers();
        final int usersCount = users.size();
        final Random random = new Random();
        final int ramdomUserIndex = random.ints(0, usersCount).findFirst().getAsInt();
        return users.get(ramdomUserIndex);
    }

    @Then("^everyone should see the message in Global Chat$")
    public void everyone_should_see_the_message_in_Global_Chat() throws Throwable {
        final List<User> users = chatRoom.getUsers();
        for(User user : users) {
            History globalChatHistory = user.getGlobalChatHistory();
            Message lastMassageFromHistory = globalChatHistory.last();

            assertThat(lastMassageFromHistory.getAuthor(), is(anyUser));
            assertThat(lastMassageFromHistory.getContent(), is(BROADCAST_MESSAGE));
        }
    }

    @Given("^some user creates chat group$")
    public void some_user_creates_chat_group() throws Throwable {
        someUser = new Member("someUser", chatRoom);
        userListOfChatCreatedBySomeUser = chatRoom.getUsers().subList(0,1);
        chatCreatedBySomeUser = chatRoom.createChat(CHAT_NAME, someUser, userListOfChatCreatedBySomeUser);
    }

    @Given("^the one adds there several users from Company Room$")
    public void the_one_adds_there_several_users_from_Company_Room() throws Throwable {
        //added at the same place where created
    }

    @When("^creator of the group sends a message$")
    public void creator_of_the_group_sends_a_message() throws Throwable {
        someUser.broadcast(chatCreatedBySomeUser, CHAT_MSG);
    }

    @Then("^everyone sees the message in this chat of the group$")
    public void everyone_sees_the_message_in_this_chat_of_the_group() throws Throwable {
        for (final User user : userListOfChatCreatedBySomeUser) {
            final Message lastMsg = user.getChatHistory(CHAT_NAME).last();

            assertThat(lastMsg.getAuthor(), is(someUser));
            assertThat(lastMsg.getContent(), is(CHAT_MSG));
        }
    }


    @Then("^users who are not in this group should not see the message$")
    public void users_who_are_not_in_this_group_should_not_see_the_message() throws Throwable {
        final List<User> usersNotInChatCreatedBySomeUser = chatRoom.getUsers().stream().filter(
                user -> !userListOfChatCreatedBySomeUser.contains(user)
        ).collect(Collectors.toList());

        for (final User user : usersNotInChatCreatedBySomeUser) {
            final Message lastMsg = user.getChatHistory(CHAT_NAME).last();

            assertThat(lastMsg, is(nullValue()));
        }
    }

}
