package com.example.cyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


public class User_Chat_Activity extends AppCompatActivity {

    Button sendButton;
    Button connectButton;
    Button backButton;
    EditText sendMessageText;
    TextView TicketTitle;
    TextView chatMessages;
    String chat;
    SimpleDateFormat sdf;
    String dateTime;

    //Actor actor = getParentActivityIntent().getParcelableExtra("actor");
    int ticketID;
    int ticketPosition;
    int actorId;
    String username;
    String ticketTitle;
    URI uri;


    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        Intent intent = getIntent();

        ticketID = intent.getIntExtra("ticketID",0);
        actorId = intent.getIntExtra("actorId",1); //not zero as that is the admin account
        username = intent.getStringExtra("userName");
        ticketTitle = intent.getStringExtra("ticketTitle");
        ticketPosition = intent.getIntExtra("TicketPosition", 0);

        TicketTitle = (TextView) findViewById(R.id.Ticket_Title_UserChatActivity);
        TicketTitle.setText(ticketTitle);
        backButton = (Button) findViewById(R.id.backButton_UserChatActivity);
        sendButton = (Button) findViewById(R.id.sendButton_UserChatActivity);
        connectButton = (Button) findViewById(R.id.connectButton_UserChatActivity);
        //Need a connect button maybe

        sdf = new SimpleDateFormat("HH:mm, MM-dd-yyyy");
        dateTime = "";

        sendMessageText = (EditText) findViewById(R.id.chatMessage_UserChatActivity);

        chatMessages = (TextView) findViewById(R.id.chatTextView_activity_user_chat);
        chatMessages.setMovementMethod(new ScrollingMovementMethod());
        chat = "";
        chatMessages.setText("Chat is empty! Send a message");

        connectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Draft[] drafts = {
                        new Draft_6455()
                };

                String w = "ws://coms-309-051.cs.iastate.edu:8080/chat/" + ticketID + "/" + actorId;
                System.out.println("Trying URL:" + w);
                try {
                    uri = new URI(w);
                } catch (URISyntaxException e) {
                    Log.d("", "URI failed");
                }

                Log.d("Socket:", "Trying socket");
                cc = new WebSocketClient(uri, (Draft) drafts[0]) {
                    @Override
                    protected Collection<WebSocket> getConnections() {
                        Log.d("","getting connections");
                        return super.getConnections();
                    }

                    @Override
                    public boolean connectBlocking() throws InterruptedException {
                        Log.d("","connection blocked");
                        return super.connectBlocking();
                    }

                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        Log.d("OPEN", "run() returned: " + "is connecting");
                        System.out.println("Websocket opened");
                        chatMessages.setText("Connecting...");
                    }

                    @Override
                    public void onMessage(String message) {
                        Log.d("","run() returned: " + message);
                        chat = chat + message;
                        chatMessages.setText(chat);

                        //Scrolls to bottom of chat when new message is received.
                        int scrollAmount = chatMessages.getLayout().getLineTop(chatMessages.getLineCount()) - chatMessages.getHeight();
                        if (scrollAmount > 0) {
                            chatMessages.scrollTo(0,scrollAmount);
                        } else {
                            chatMessages.scrollTo(0,0);
                        }
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        Log.d("CLOSE", "onClose() returned: " + reason);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("Exception:", e.toString());
                    }
                };




            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cc.send(sendMessageText.getText().toString());
                    dateTime = sdf.format(new Date());
                    chat = chat + "\n" + username + ": " + dateTime + "\n" + sendMessageText.getText().toString();
                    chatMessages.setText(chat);

                    //Scrolls to bottom of chat
                    int scrollAmount = chatMessages.getLayout().getLineTop(chatMessages.getLineCount()) - chatMessages.getHeight();
                    if (scrollAmount > 0) {
                        chatMessages.scrollTo(0,scrollAmount);
                    } else {
                        chatMessages.scrollTo(0,0);
                    }
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.toString());
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(User_Chat_Activity.this, ViewUserTicketActivity.class);
                intent1.putExtra("TicketPosition", ticketPosition);
                intent1.putExtra("UserId", actorId);
                startActivity(intent1);
            }
        });


    }




}