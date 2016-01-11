Feature: Broadcast messages

  Rules:
  - admin can add users by emails to chat;
  - all users are members of Global Company Chat group by default;
  - users can create private groups and add members there;
  - admin can review any group;
  - admin can specify some words which are forbidden to be mentioned in any group;
  - forbidden word costs some value which is sent to an accounter at the end of month and these value
  will be used to buy some cakes for kitchen from salary of spammer:)

  Questions:
  -

  Background:
    Given there are several users added to Company Room

  Scenario: Broadcasting messages in global chat
    When any of them sends some message
    Then everyone should see the message in Global Chat


  Scenario: Broadcasting messages in chat group from creator
    Given some user creates chat group
    And the one adds there several users from Company Room
    When creator of the group sends a message
    Then everyone sees the message in this chat of the group