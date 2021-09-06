package com.example.ns_demo_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b1, b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.mainActivity_buttonCounter);
        b2 = (Button) findViewById(R.id.mainActivity_buttonLogin);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Counter", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, CounterActivity.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i2);
            }
        });


    };
}