package com.example.ns_demo_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginActivity_loginButton);
        Button backButton = findViewById(R.id.loginActivity_backButton);

        final EditText[] username = {(EditText) findViewById(R.id.loginActivity_usernameInput)};
        final EditText[] password = {(EditText) findViewById(R.id.loginActivity_passwordInput)};

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username[0] = (EditText) findViewById(R.id.loginActivity_usernameInput);
                password[0] = (EditText) findViewById(R.id.loginActivity_passwordInput);
                String user = username[0].toString();
                if (username[0].length() > 0 && password[0].length() > 0) {
                    Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
                    i.putExtra("username", user);
                    startActivity(new Intent(view.getContext(), WelcomeActivity.class));

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });




    }
}