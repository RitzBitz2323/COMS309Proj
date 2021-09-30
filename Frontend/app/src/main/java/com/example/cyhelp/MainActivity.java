package com.example.cyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.userButton);
        b2 = (Button) findViewById(R.id.techButton);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(MainActivity.this, CreateNewTicketActivity.class);
                startActivity(userIntent);
            }
        });

        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent techIntent = new Intent(MainActivity.this, TechTicketFinderMapActivity.class);
                startActivity(techIntent);
            }
        });


    }


}