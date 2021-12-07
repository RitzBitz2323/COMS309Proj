package com.example.cyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class User_Chat_Activity extends AppCompatActivity {

    Button sendButton;
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
    int actorType;
    String username;
    String ticketTitle;
    URI uri;


    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        Intent intent = getIntent();

        ticketID = intent.getIntExtra("ticketID",1);
        actorId = intent.getIntExtra("actorId",1);
        actorType = intent.getIntExtra("actorType", 0);
        username = intent.getStringExtra("userName");
        ticketTitle = intent.getStringExtra("ticketTitle");
        ticketPosition = intent.getIntExtra("TicketPosition", 0);
        TicketTitle = (TextView) findViewById(R.id.Ticket_Title_UserChatActivity);
        TicketTitle.setText(ticketTitle);
        backButton = (Button) findViewById(R.id.backButton_UserChatActivity);
        sendButton = (Button) findViewById(R.id.sendButton_UserChatActivity);
        sdf = new SimpleDateFormat("HH:mm, MM-dd-yyyy");
        dateTime = "";
        sendMessageText = (EditText) findViewById(R.id.chatMessage_UserChatActivity);
        chatMessages = (TextView) findViewById(R.id.chatTextView_activity_user_chat);
        chatMessages.setMovementMethod(new ScrollingMovementMethod());
        chat = "";
        chatMessages.setText("Chat is empty! Send a message");

        String url = "http://coms-309-051.cs.iastate.edu:8080/actors/" + actorId + "/"+ ticketID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    username = jsonObject.getString("username");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                String ErrorMessage = "";
                if (error instanceof NetworkError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof ServerError) {
                    ErrorMessage = "Server not found. Please try again later.";
                } else if (error instanceof AuthFailureError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof ParseError) {
                    ErrorMessage = "Parse Error! Please try again later.";
                } else if (error instanceof NoConnectionError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof TimeoutError) {
                    ErrorMessage = "Connection TimeOut. Check your connection!";
                }
                Toast.makeText(getApplicationContext(),ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });






        String w = "ws://coms-309-051.cs.iastate.edu:8080/chat/" + ticketID + "/" + actorId;
        System.out.println("Trying URL:" + w);
        try {
            uri = new URI(w);
        } catch (URISyntaxException e) {
            Log.d("", "URI failed");
        }

        Log.d("Socket:", "Trying socket");
        cc = new WebSocketClient(uri) {
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
        cc.connect();



           // }
       //});

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dateTime = sdf.format(new Date());
                    cc.send("\n" + username + ": " + dateTime + "\n" + sendMessageText.getText().toString() + "\n");
                    sendMessageText.setText("");
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(sendButton.getApplicationWindowToken(),0);
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


                if (actorType == 0) {
                    Intent intent1 = new Intent(User_Chat_Activity.this, ViewUserTicketActivity.class);
                    intent1.putExtra("TicketPosition", ticketPosition);
                    intent1.putExtra("UserId", actorId);
                    intent1.putExtra("ticketID", ticketID);
                    startActivity(intent1);
                } else if (actorType == 1) {
                    Intent intent2 = new Intent(User_Chat_Activity.this, ViewTechHomeTicket.class);
                    intent2.putExtra("ticketPosition", ticketPosition);
                    intent2.putExtra("techID", actorId);
                    intent2.putExtra("ticketID", ticketID);
                    startActivity(intent2);
                }


            }
        });


    }




}