package com.example.hello_world;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exclmBtn(View view) {
        TextView helloWorld = findViewById(R.id.helloWorldMessage);
        helloWorld.setText(helloWorld.getText() + "!");
    }
}