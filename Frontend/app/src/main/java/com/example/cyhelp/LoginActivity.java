package com.example.cyhelp;

import static com.example.cyhelp.R.id.editTextTextPassword_loginActivity;
import static com.example.cyhelp.R.id.editTextTextPersonName_loginActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton_loginActivity);

        final EditText[] username = (EditText[]) findViewById(R.id.editTextTextPersonName_loginActivity);
        final EditText[] password = (EditText[]) findViewById(R.id.editTextTextPassword_loginActivity);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username[0] = (EditText) findViewById(editTextTextPersonName_loginActivity);
                password[0] = (EditText) findViewById(editTextTextPassword_loginActivity);
                String user = username[0].toString();


                Intent i = new Intent(LoginActivity.this, CreateNewTicketActivity.class);
                i.putExtra("username", user);
                startActivity(new Intent(view.getContext(), CreateNewTicketActivity.class));
            }

        }
    }
}






}
