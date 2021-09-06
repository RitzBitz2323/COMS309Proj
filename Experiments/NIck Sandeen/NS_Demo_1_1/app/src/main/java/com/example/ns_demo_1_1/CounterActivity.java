package com.example.ns_demo_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        Button incrementButton = findViewById(R.id.counterActivity_incrementButton);
        Button decrementButton = findViewById(R.id.counterActivity_decrementButton);
        Button resetButton = findViewById(R.id.counterActivity_resetButton);
        Button backButton = findViewById(R.id.counterActivity_backButton);
        TextView counterText = findViewById(R.id.counterActivity_countText);

        Integer[] count = {0};


        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0]++;
                counterText.setText(count[0].toString());
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[0] != 0) {
                    count[0]--;
                    counterText.setText(count[0].toString());
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] = 0;
                counterText.setText(count[0].toString());
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