package com.anitang99.socialapp.Messaging;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anitang99.socialapp.R;
import com.anitang99.socialapp.Utils.Net.MessageDecoder;
import com.anitang99.socialapp.Utils.Net.SocketManager;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessageListActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private ArrayList<Message> messageList;

    private EditText messageInput;
    private Button sendButton;

    private static SocketManager socketManager;

    private static final String URL_LOCAL = "ws://10.0.2.2:8080/";
    private static final String URL_REMOTE = "ws://pkmnruler.noip.me:8080/";

    private String username;
    private String chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        username = this.getIntent().getStringExtra("username");
        chatID = "1";

        socketManager = SocketManager.getInstance();

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        messageInput = findViewById(R.id.edittext_chatbox);
        sendButton = findViewById(R.id.button_chatbox_send);
        sendButton.setEnabled(false);

        messageList = new ArrayList<>();
        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = messageInput.getText().toString();
                Log.d("Send", chat);
                UserMessage message = new UserMessage(chat, new User(username));
                mMessageAdapter.addLast(message);
                messageInput.setText("");
                JSONObject json = new JSONObject();
                try {
                    json.put("username", username);
                    json.put("message", chat);
                } catch (Exception e){
                    e.printStackTrace();
                }
                socketManager.getSocket().send(json.toString());
            }
        });

        try {
            socketManager.setMessageListActivity(this);
            socketManager.setUsername(username);
            socketManager.connectSocket(URL_REMOTE + "chat/" + chatID + "?username=" + socketManager.getUsername());
            Thread.sleep(2000);
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public String getUsername() {
        return username;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketManager.forceClose();
    }

    public void addMessage(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessageAdapter.addLast(MessageDecoder.decodeMessage(message));
            }
        });

    }

    public void toast(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });

    }
}
