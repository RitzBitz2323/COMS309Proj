package com.example.textfieldapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputText = findViewById(R.id.activityMainTextInput1);
        Button nextPageButton = findViewById(R.id.activityMainButtonNextPage);

        inputText.setText(Globals.displayText);

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.displayText = inputText.getText().toString();
                startActivity(new Intent(view.getContext(), DisplayPage.class));
            }
        });
    }
}