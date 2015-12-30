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

  Scenario: Broadcasting messages in global chat
    Given there are several users added to Company Chat
    When any of them sends some message
    Then everyone should see the message in Global Chat
