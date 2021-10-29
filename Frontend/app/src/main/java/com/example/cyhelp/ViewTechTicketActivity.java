package com.example.cyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ViewTechTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tech_ticket);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 2);
        System.out.println("ID of ticket: " + id);
    }
}