package com.example.cyhelp;

import android.content.Context;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpPresenter {

    private View view;
    private SignUpModel model;
    private SignUpActivity activity;
    private Boolean ActorCreated;
    private String ErrorMessage;
    private int ActorID;
    private int ActorType;

    public SignUpPresenter(View v, Context context, SignUpActivity a) {
        this.view = v;
        this.model = new SignUpModel(context);
        this.activity = a;
    }


    public void SignUpUser(String username, String firstName,
                           String lastName, String password,
                           String address, int actorTypeID) {

        JSONObject createActor = new JSONObject();
        try {
            createActor.put("username", username);
            createActor.put("firstName", firstName);
            createActor.put("lastName", lastName);
            createActor.put("password", password.hashCode());
            createActor.put("homeAddress", address);
            createActor.put("userType", actorTypeID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        model.httpCreateUserRequest(createActor, this);
    }

    public void getActorInfo() {

        int userID = model.getUserID();
        System.out.println("User ID: " + userID);
        System.out.println("USer Type: " + model.getUserType());

        if (model.getUserType() >= 4) {
            ActorCreated = false;
            ActorType = model.getUserType();
            ErrorMessage = model.getErrorMessage();
            System.out.println(ErrorMessage);
        } else {
            ActorID = userID;
            ActorType = model.getUserType();
            ErrorMessage = "";
            ActorCreated = true;
        }
        activity.ActorCreated(ActorCreated);
    }



    public boolean CreateActor(){

        return ActorCreated;
    }



    public int getActorID() {return ActorID;}
    public int getActorType() {return ActorType;}
    public String getErrorMessage() {return ErrorMessage;}
}
