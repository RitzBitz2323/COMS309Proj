package com.example.cyhelp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;


public class User_Chat_Activity extends AppCompatActivity {

    Button sendButton;
    Actor actor = getParentActivityIntent().getParcelableExtra("actor");
    String ticketID = getParentActivityIntent().getStringExtra("ticketID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        sendButton = (Button) findViewById(R.id.SendButton_UserChatActivity);



        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Draft[] drafts = {
                        new Draft_6455()
                };

                String url = "http://coms-309-051.cs.iastate.edu:8080/chat/" + ticketID + "/" + actor.getActorID();



            }
        });




    }




}