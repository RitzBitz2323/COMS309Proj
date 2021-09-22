package com.example.cyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void techActivity(View view) {
        Intent techIntent = new Intent(this, TechActivity.class);
        startActivity(techIntent);
    }

    public void userActivity(View view) {
        Intent userIntent = new Intent(this, UserActivity.class);
        startActivity(userIntent);
    }
}