Team Project:

Assignment Description
Ideas and Implementation
Testing


Description:

For phase 1 of this project, we were tasked with creating the database side of our project, which
is to code a social media application. We chose to create a Direct Messaging social media application,
and so our main storage is in the storing of Users and Messages.

Ideas and Implementation:
Because we chose to implement Messaging, we came up with a basic outline of having User and Message classes,
as well as Databases for each of those. This is how the User, UserDatabase, Message, and the supporting Conversation,
FileMessage, TextMessages, and Conversation Database classes were created.

User.java:

This class contains the information for each individual user. 
Each user has a name, username, password, bio, and birthday. Each user has a list of friends, 
which they can add or remove other users from, as well as a list of blocked users.
Blocked users will not be able to have conversations with each other.
Along with password creation, we decided to incorporate password encryption into the User class. This will ensure a 
safer and more secure experience for Users.

ActionNotAllowedException.java:

This class represents the exception that is thrown when a User performs an illegal action. 
This exception may be thrown when the User's chosen username contains a space, or when the User attempts to add or 
block another user whom they have already added or blocked, respectively. It could also be thrown when a Message's index
is out of bounds. 

Message.java:

This class represents a Message that a User sends. Each message has a time at which it was sent, the User who sent it,
and a message index (which denotes the order in the conversation that this Message was sent).

TextMessage.java extends Message.java:

This class represents a text sent between Users. Its functionality includes editing/changing Text Messages, and comparing
two messages to check their equality. Finally, this class has a toString functionality which displays the User who sent 
the message, the time the message was sent, and the content of the message. 

Conversation.java:

This class contains the information for every conversation in the database. Each conversation has a Conversation Name, 
which consists of the names of the Users involved in the conversation separated by an underscore, and a unique 
conversationID. The unique conversationID will make it much easier to store and find the conversations in the database.

ConversationDatabase.java implements Database:

The conversation database stores every message that has ever been sent by any of the Users. The database can return
which conversations the User is involved in, as well as write all of the conversation names to a file. Also, given a 
conversation name, the database can retrieve all messages from that conversation. This will be helpful for implementing
a search option. 
