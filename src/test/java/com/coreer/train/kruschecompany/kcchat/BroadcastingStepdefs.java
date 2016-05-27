package com.coreer.train.kruschecompany.kcchat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by aieremenko on 12/30/15.
 */
public class BroadcastingStepdefs {


    private ChatRoom chatRoom;

    private User admin = new Admin("admin");

    private User anyUser;

    private User senderInCurrentPrivateGroupChat;
    private Chat currentPrivateGroupChat;

    private List<User> applicantsForCurrentPrivateChat;
    private List<Message> someMessagesOfApplicants;


    public static class ChatRoomUser {
        public String nickname;
        public String email;
    }

    @Given("^there are several users added to Company Room:$")
    public void there_are_several_users_added_to_Company_Room(List<ChatRoomUser> users) throws Throwable {
        chatRoom = new ChatRoom(admin);
        final List<User> chatRoomMemebers = users.stream().map(user -> new Member(user.nickname, chatRoom)).collect(Collectors.toList());
        chatRoom.addUsers(chatRoomMemebers);
    }

    public static class PrivateGroupChat {
        public String chatName;
        public String creator;
        public String members;
    }

    @Given("^there are several created private group chats:$")
    public void there_are_several_created_private_group_chats(List<PrivateGroupChat> privateGroupChats) throws Throwable {

        privateGroupChats.stream().forEach(privateGroupChat -> {
            final List<String> memberUsernames = Arrays.asList(privateGroupChat.members.split(","));
            final List<User> memebers = chatRoom.getUsers().stream()
                    .filter(user -> memberUsernames.contains(user.getNickname()))
                    .collect(Collectors.toList());
            final User creator = chatRoom.getUser(privateGroupChat.creator);
            creator.createChat(privateGroupChat.chatName, memebers);
        });
    }


    @When("^(.*?) of them sends some (.*?) in Global Chat$")
    public void any_of_them_sends_some_message_in_Global_Chat(String nickname, String message) throws Throwable {
        anyUser = chatRoom.getUser(nickname);
        anyUser.broadcast(message);
    }


    @Then("^everyone should see the (.*?) in Global Chat$")
    public void everyone_should_see_the_message_in_Global_Chat(String message) throws Throwable {
        final List<User> users = chatRoom.getUsers();
        for (User user : users) {
            History globalChatHistory = user.getGlobalChatHistory();
            Message lastMassageFromHistory = globalChatHistory.last();

            assertThat(lastMassageFromHistory.getAuthor(), is(anyUser));
            assertThat(lastMassageFromHistory.getContent(), is(message));
        }
    }

    @When("^(.*?) from (.*?) sends a (.*?)$")
    public void someone_from_private_group_chat_sends_a_message(
            String senderName, String privateGroupChatName, String message) {
        senderInCurrentPrivateGroupChat = chatRoom.getUser(senderName);
        currentPrivateGroupChat = chatRoom.getChat(privateGroupChatName);
        senderInCurrentPrivateGroupChat.broadcast(currentPrivateGroupChat, message);
    }


    @Then("^everyone sees the (.*?) in the (.*?)$")
    public void everyone_sees_the_message_in_the_private_group_chat(String message, String privateGroupChat) throws Throwable {
        final Chat chat = chatRoom.getChat(privateGroupChat);
        chat.getAttendees().stream().forEach(u -> {
            final Message lastMsg = u.getChatHistory(privateGroupChat).last();
            assertThat(lastMsg.getContent(), is(message));
            assertThat(lastMsg.getAuthor(), is(senderInCurrentPrivateGroupChat));
        });
    }


    @Then("^users who are not in this group should not see the (.*?)$")
    public void users_who_are_not_in_this_group_should_not_see_the_message(String message) throws Throwable {
        final List<User> attendees = currentPrivateGroupChat.getAttendees();
        final List<User> usersNotInChatCreatedBySomeUser = chatRoom.getUsers().stream().filter(
                user -> !attendees.contains(user)
        ).collect(Collectors.toList());

        for (final User user : usersNotInChatCreatedBySomeUser) {
            final History chatHistory = user.getChatHistory(currentPrivateGroupChat.getName());

            assertThat(chatHistory, instanceOf(NullHistory.class));
        }
    }


    @When("^(.*?) creates private chat with name (.*?)$")
    public void user_creates_private_chat_with_name(final String creatorName, final String chatName) throws Throwable {
        this.anyUser = chatRoom.getUser(creatorName);
        this.currentPrivateGroupChat = this.anyUser.createChat(chatName, new ArrayList<>());

    }

    @When("^(.*?) adds (.*?) to the chat$")
    public void user_adds_Skywalker_Jabba_and_Obi_wan_to_the_chat(final String creatorName, final String attendeesListStr) throws Throwable {
        assert creatorName == anyUser.getNickname();

        final List<String> attendees = Arrays.asList(attendeesListStr.split("\\s*(,|and)\\s*"));
        applicantsForCurrentPrivateChat = new ArrayList<>();

        for (int i = 0; i < attendees.size(); i++) {
            final User attendee = chatRoom.getUser(attendees.get(i));
            this.applicantsForCurrentPrivateChat.add(attendee);
            this.currentPrivateGroupChat.addAttendee(
                    chatRoom.getUser(creatorName),
                    attendee
            );
        }
    }


    @Then("^these users can read messages there from each other$")
    public void these_users_can_read_messages_there_from_each_other() throws Throwable {
        someMessagesOfApplicants = new ArrayList<>();
        for(User user : applicantsForCurrentPrivateChat) {
            final String msgContent = RandomStringUtils.randomAlphanumeric(10);
            final Message msg = user.broadcast(currentPrivateGroupChat, msgContent);
            someMessagesOfApplicants.add(msg);
        }
        for(User user : applicantsForCurrentPrivateChat) {
            final History chatHistory = user.getChatHistory(currentPrivateGroupChat.getName());
            chatHistory.fromLastToFirst().containsAll(someMessagesOfApplicants);
        }


    }

    @Then("^users, who are not members, cannot read the messages$")
    public void users_who_are_not_members_cannot_read_the_messages() throws Throwable {
        chatRoom.getUsers().stream().filter(user -> !applicantsForCurrentPrivateChat.contains(user) && user != anyUser)
                .forEach(otherUser -> {
                    final History chatHistory = otherUser.getChatHistory(currentPrivateGroupChat.getName());
                    for(Message msg: someMessagesOfApplicants) {
                        assertThat(chatHistory.fromLastToFirst().contains(msg), is(false));
                    }
                });
    }



}
