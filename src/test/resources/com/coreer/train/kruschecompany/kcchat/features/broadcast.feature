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
    Given there are several users added to Company Room:
      | nickname    | email                 |
      | Skywalker   | skw@gmail.com         |
      | Obi-wan     | obyone@yahoo.com      |
      | Yoda        | yoda@nirvana.org      |
      | Leia        | leia@shopping.com     |
      | Jabba       | jabba@foodcourt.com   |
      | Darth Vader | vader@hotgirl.com     |
      | Kylo Ren    | kylo.ren@hotgirls.com |
    And there are several created private group chats:
      | chat name     | creator   | members                     |
      | Good guys     | Skywalker | Obi-wan,Yoda,Leia           |
      | Train group   | Yoda      | Obi-wan,Skywalker,Leia      |
      | Bad guys      | Kylo Ren  | Darth Vader,Jabba           |
      | Strange group | Jabba     | Obi-wan,Yoda,Leia,Skywalker |


  Scenario Outline: Broadcasting messages in global chat
    When <any user> of them sends some <message> in Global Chat
    Then everyone should see the <message> in Global Chat
    Examples:
      | any user | message               |
      | Jabba    | Kva-kva               |
      | Obi-wan  | Let's get out of here |
      | Yoda     | The force awakens     |


  Scenario Outline: Broadcasting messages in private group chat
    When <someone> from <private group chat> sends a <message>
    Then everyone sees the <message> in the <private group chat>
    And users who are not in this group should not see the <message>
    Examples:
      | private group chat | someone   | message                                         |
      | Good guys          | Skywalker | vse propalo!                                    |
      | Good guys          | Yoda      | Zakroj rot!                                     |
      | Train group        | Yoda      | Tryapki soberites'!                             |
      | Bad guys           | Kylo Ren  | I wanna some money and you will get their plans |
      | Strange group      | Leia      | Let's avenge!                                   |


  Scenario: Any user can create private chat
    When random user creates private chat
    Then the user can add any other users of Company Room to the chat
    And these users can read messages there from each other
    But users, whos are not members, cannot read the messages
