package com.example.ns_demo_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button logOutButton = findViewById(R.id.welcomeActivity_logOutButton);

        String username = getIntent().getStringExtra("username");
        TextView welcomeMessage = findViewById(R.id.welcomeActivity_usernameText);
        welcomeMessage.setText("@" + username);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });


    }
}