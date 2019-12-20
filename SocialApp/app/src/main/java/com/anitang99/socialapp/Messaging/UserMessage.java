package com.anitang99.socialapp.Messaging;

public class UserMessage implements Message {

    private String message;

    private User sender;

    private long createdAt;

    public UserMessage(String message, User sender){
        this.message = message;
        this.sender = sender;
        this.createdAt = System.currentTimeMillis();
    }

    /**
     * Returns the message string.
     * @return Returns a String.
     */
    public String getMessage(){
        return message;
    }

    /**
     * Returns the User object associated with the message
     * @return Returns a User object.
     */
    public User getSender(){
        return sender;
    }

    /**
     * Returns the long representing the time the UserMessage object was created.
     * @return Returns a long.
     */
    public long getCreatedAt(){
        return createdAt;
    }
}
