package com.anitang99.socialapp.Utils.Net;

import android.util.Log;

import com.anitang99.socialapp.Messaging.User;
import com.anitang99.socialapp.Messaging.UserMessage;

import org.json.JSONObject;

public class MessageDecoder {

    public static UserMessage decodeMessage(String rawMsg){
        if(rawMsg == null && rawMsg.length() == 0)
            return  null;
        try {
            JSONObject json = new JSONObject(rawMsg);
            UserMessage message = new UserMessage(json.getString("message"), new User(json.getString("username")));
            return message;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
