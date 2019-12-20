package com.anitang99.socialapp.Messaging;

public interface Message {
    /**
     * Returns the message string.
     * @return Returns a String.
     */
    String getMessage();

    /**
     * Returns the User object associated with the message
     * @return Returns a User object.
     */
    User getSender();

    /**
     * Returns the long representing the time the UserMessage object was created.
     * @return Returns a long.
     */
    long getCreatedAt();
}
