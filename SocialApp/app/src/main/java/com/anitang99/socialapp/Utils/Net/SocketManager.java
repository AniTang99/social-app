package com.anitang99.socialapp.Utils.Net;


import android.widget.Toast;

import com.anitang99.socialapp.Messaging.MessageListActivity;
import com.anitang99.socialapp.Messaging.MessageListAdapter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class SocketManager {

    /**
     * The constant TAG.
     */
    public static final String TAG = SocketManager.class.getSimpleName();
    private static WebSocket socket;
    private static OkHttpClient client;
    private static SocketManager instance;
    private String lastMessage = null;
    private MessageListActivity messageListActivity;
    private boolean connected = false;
    private String username;

    private static final String URL_REMOTE_ALT = "ws://192.168.0.59:8080/";

    private static final int ATTEMPT_LIMIT = 2;
    int attempts = 1;

    private SocketManager(){
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public synchronized static SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
            client = new OkHttpClient.Builder()
                    .connectTimeout(500, TimeUnit.MILLISECONDS)
                    .writeTimeout(500, TimeUnit.MILLISECONDS)
                    .readTimeout(500, TimeUnit.MILLISECONDS)
                    .build();
        }
        return instance;
    }

    /**
     * Connect socket.
     *
     * @param url the URL to connect to
     */
    public void connectSocket(String url) {
        if(socket==null && client != null){
            Request request = new Request.Builder().url(url).build();

            socket = client.newWebSocket(request, new ChatListener(instance));

        }
    }

    public void setLastMessage(String lastMessage) {
        messageListActivity.addMessage(lastMessage);
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setMessageListActivity(MessageListActivity messageListActivity) {
        this.messageListActivity = messageListActivity;
    }

    /**
     * Gets socket.
     *
     * @return the socket
     */
    public WebSocket getSocket(){
        return socket;
    }

    /**
     * Sets socket.
     *
     * @param socket the socket
     */
    public void setSocket(WebSocket socket) {
        this.socket = socket;
    }

    /**
     * Gets the OkHttpClient object stored.
     * @return the OkHttpClient
     */
    public OkHttpClient getClient(){
        return this.client;
    }

    /**
     * Sets the client. Should be called before any socket methods are used.
     * @param client    OkHttpClient
     */
    public void setClient(OkHttpClient client){
        this.client = client;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void toast(String message){
        messageListActivity.toast(message);
    }

    /**
     * Forcibly closes the connection w/o reason.
     */
    public void forceClose(){
        if(socket != null){
            socket.cancel();
            socket = null;
        }
    }

    /**
     * Safely closes the socket with reason.
     * @param code      Status code as defined by Section 7.4 of RFC 6455.
     * @param reason    Reason for shutting down or null.
     */
    public void close(int code, String reason){
        if(socket != null){
            socket.close(code, reason);
            socket = null;
        }
    }

    public boolean isConnected(){
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void retryConnection(){
        setSocket(null);

        while (attempts < ATTEMPT_LIMIT){
            toast("Connection attempts 1/2 failed...");
            connectSocket(URL_REMOTE_ALT + "chat/" + "1" + "?username=" + getUsername());
            attempts++;
        }
        toast("Connection Failed");
    }
}
