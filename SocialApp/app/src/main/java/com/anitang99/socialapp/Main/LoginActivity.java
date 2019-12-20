package com.anitang99.socialapp.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anitang99.socialapp.Messaging.MessageListActivity;
import com.anitang99.socialapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private Button button;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.etUsername);
        button = findViewById(R.id.bLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = username.getText().toString();
                nextScreen();
            }
        });
    }

    private void nextScreen(){
        Intent intent = new Intent(this, MessageListActivity.class);
        intent.putExtra("username",userName);
        startActivity(intent);
    }
}
