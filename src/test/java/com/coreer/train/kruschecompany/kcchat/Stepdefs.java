package com.coreer.train.kruschecompany.kcchat;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by aieremenko on 12/30/15.
 */
public class Stepdefs {
    private Chat chat;

    @Given("^there are several users added to Company Chat$")
    public void there_are_several_users_added_to_Company_Chat() throws Throwable {
        chat = new Chat();
        final User skywokker = new User("Skywokker");
        final User obione = new User("Obione");
        final User yoda = new User("Yoda");
        final User[] users = {skywokker, obione, yoda};
        chat.addUsers(Arrays.asList(users));
    }

    @When("^any of them sends some message$")
    public void any_of_them_sends_some_message() throws Throwable {
        final User anyUser = getRandomUser(chat);
        anyUser.broadcast("Hi all!");
    }

    private User getRandomUser(Chat chat) {
        List<User> users = chat.getUsers();
        final int usersCount = users.size();
        final Random random = new Random();
        final int ramdomUserIndex = random.ints(0, usersCount).findFirst().getAsInt();
        return users.get(ramdomUserIndex);
    }

    @Then("^everyone should see the message in Global Chat$")
    public void everyone_should_see_the_message_in_Global_Chat() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
