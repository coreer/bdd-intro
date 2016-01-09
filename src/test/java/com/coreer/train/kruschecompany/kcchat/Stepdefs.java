package com.coreer.train.kruschecompany.kcchat;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by aieremenko on 12/30/15.
 */
public class Stepdefs {
    public static final String BROADCAST_MESSAGE = "Hi all!";
    private ChatRoom chatRoom;
    private User anyUser;
    private User admin = new Admin("admin");

    @Given("^there are several users added to Company Room$")
    public void there_are_several_users_added_to_Company_Room() throws Throwable {
        chatRoom = new ChatRoom(admin);
        final User skywokker = new Member("Skywokker", chatRoom);
        final User obione = new Member("Obione", chatRoom);
        final User yoda = new Member("Yoda", chatRoom);
        final User[] users = {skywokker, obione, yoda};
        chatRoom.addUsers(Arrays.asList(users));
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
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^the one adds there several users from Company Room$")
    public void the_one_adds_there_several_users_from_Company_Room() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^creator of the group sends a message$")
    public void creator_of_the_group_sends_a_message() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^everyone sees the message in this chat of the group$")
    public void everyone_sees_the_message_in_this_chat_of_the_group() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
