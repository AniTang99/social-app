package com.anitang99.socialapp.Utils.Net;

import android.util.Log;

import com.anitang99.socialapp.Messaging.MessageListAdapter;
import com.anitang99.socialapp.Messaging.UserMessage;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatListener extends WebSocketListener {

    private SocketManager socketManager;

    // Custom codes
    private static final String CONNECTED_MESSAGE = "connected";
    public static final String TAG = ChatListener.class.getSimpleName();

    public ChatListener(SocketManager socketManager){
        this.socketManager = socketManager;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        //TODO
        Log.d(TAG, "Entered into onOpen");
        socketManager.toast("Connected to chatroom");

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        //TODO
        Log.d(TAG, "Entered into onMessage");
        if(text.equals(CONNECTED_MESSAGE)) {
            socketManager.setConnected(true);
            return;
        }
        else {
            socketManager.setLastMessage(text);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        //TODO
        Log.d(TAG, "Entered into onClosing");

    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        //TODO
        Log.d(TAG, "Entered into onClosed");

    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        //TODO
        Log.d(TAG, "Entered into onFailure");
        t.printStackTrace();

        socketManager.retryConnection();
        if (response != null)
            Log.d(TAG,response.toString());

    }

}
