package com.anitang99.socialapp.Messaging;

public class User {
    private String username;

    private String nickname;

    private String profileUrl;

    public User(String username){
        this.username = username;
        this.nickname = username;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getNickname() {
        return nickname;
    }
}
