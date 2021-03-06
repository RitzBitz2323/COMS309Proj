package com.example.cyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity is the landing page when the actor opens the application.
 *
 * @author Parthiv Ganguly
 */
public class MainActivity extends AppCompatActivity {

    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.loginButton);
        b2 = (Button) findViewById(R.id.userButton);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(userIntent);
            }
        });

        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent techIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(techIntent);
            }
        });


    }

}