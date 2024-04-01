# CS 180 Team Project Phase 1

- Assignment Description
- Ideas and Implementation
- Testing

## Description

For phase 1 of this project, we were tasked with creating the database side of our project, which
is to code a social media application. We chose to create a Direct Messaging social media application,
and so our main storage is in the storing of Users and Messages.

Ideas and Implementation:
Because we chose to implement Messaging, we came up with a basic outline of having User and Message classes,
as well as Databases for each of those. This is how the User, UserDatabase, Message, and the supporting Conversation,
FileMessage, TextMessages, and Conversation Database classes were created.

### User.java

This class contains the information for each individual user. 
Each user has a name, username, password, bio, and birthday. Each user has a list of friends, 
which they can add or remove other users from, as well as a list of blocked users.
Blocked users will not be able to have conversations with each other.
Along with password creation, we decided to incorporate password encryption into the User class. This will ensure a 
safer and more secure experience for Users.

#### Testing User.java

In order to test the User class and its various methods, several test cases were created. The add/block/remove user
methods were tested in both successful and unsuccessful cases (namely, when an exception was or was not thrown). 
We also tested the method which compared two Users alphabetically by their first names, with several different scenarios.
Finally, the encrypt method for passwords was tested by comparing the program's output with the expected output. 

### ActionNotAllowedException.java:

This class represents the exception that is thrown when a User performs an illegal action. 
This exception may be thrown when the User's chosen username contains a space, or when the User attempts to add or 
block another user whom they have already added or blocked, respectively. It could also be thrown when a Message's index
is out of bounds. 

### Message.java:

This class represents a Message that a User sends. Each message has a time at which it was sent, the User who sent it,
and a message index (which denotes the order in the conversation that this Message was sent).

#### TextMessage.java extends Message.java:

This class represents a text sent between Users. Its functionality includes editing/changing Text Messages, and comparing
two messages to check their equality. Finally, this class has a toString functionality which displays the User who sent 
the message, the time the message was sent, and the content of the message.

#### Testing TextMessage.java and Message.java

There are two main tests written in UserTests.java for Message.java methods. The first one is a constructor method and
it simply checks, using the getter methods, that the fields in the newly created Message object
are equivalent to what they're expected to be. The second test, tests if an index setter method does
what it's expected to do. In our implementation, adding (sending) a message in a conversation
requires it's associated method to change the
message index of the new message to the size of the message ArrayList, so it's crucial
that this method functions. 

There are several tests for TextMessage.java. The first one test the constructor in an identical way to how
Message.java's constructor is tested. The second one tests the edit message method in the subclass,
by comparing the final rawMessage field of a TextMessage object to what it's expected to be. The third checks
our equality method using a simple assertTrue method call. The final test tests our toString method, which converts
the Message object into a string for file writing. 

### Conversation.java:

This class contains the information for every conversation in the database. Each conversation has a Conversation Name, 
which consists of the names of the Users involved in the conversation separated by an underscore, and a unique 
conversationID. The unique conversationID will make it much easier to store and find the conversations in the database.

### ConversationDatabase.java implements Database:

The conversation database stores every message that has ever been sent by any of the Users. The database can return
which conversations the User is involved in, as well as write all of the conversation names to a file. Also, given a 
conversation name, the database can retrieve all messages from that conversation. This will be helpful for implementing
a search option. 

#### Testing ConversationDatabase.java and Conversation.java

Because the Conversation.java constructor inherently relies on the ConversationDatabase constructor,
one encompassing test was written in order to test both. This test just compares the expected
size of the fields, as well as their values. To check the available conversation
method, an empty file was created and read through, and an assertEqual method call was used to check
that the expected size of the returned ArrayList was what equal to what it was actually. The next two 
methods test the addUser and removeUser methods, and they both use assertEquals methods
to check that the size of the user ArrayLists are what they are expected to be after the methods
have been called. The addMessage and deleteMessage accomplish this in the exact same way, using the assertEquals
method call. 
