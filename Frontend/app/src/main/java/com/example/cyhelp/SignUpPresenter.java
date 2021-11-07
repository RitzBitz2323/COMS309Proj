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

    public SignUpPresenter(View v, SignUpModel model, SignUpActivity a) {
        this.view = v;
        this.model = model;
        this.activity = a;
    }

    /**
     * This method is called by the SignUpActivity when the user clicks the Sign Up button.
     * It takes all the information entered by the user (which is passed into this method),
     * and creates a new JSON object which will be sent to the server
     * @param username username entered by user
     * @param firstName first name entered by user
     * @param lastName last name entered by user
     * @param password password entered by user
     * @param address home address entered by user
     * @param actorTypeID the type of actor (user, technician, admin, etc.)
     */
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

    /**
     * This method is similar to the one called by the SignUpActivity when the user clicks the Sign Up button.
     * It takes all the information entered by the user (which is passed into this method),
     * and adds it to an existing JSON object for the actor, which will be sent to the server
     * @param username username entered by user
     * @param firstName first name entered by user
     * @param lastName last name entered by user
     * @param password password entered by user
     * @param address home address entered by user
     * @param actorTypeID the type of actor (user, technician, admin, etc.)
     * @param actor an existing JSON object representing the actor
     */
    public void SignUpUser(String username, String firstName,
                           String lastName, String password,
                           String address, int actorTypeID, JSONObject actor) {

        JSONObject createActor = actor;
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

    /**
     * This method is used fetch actor information like actor ID and actor type (user, technician, etc.).
     * It then called the ActorCreated method of SignUpActivity which is responsible for starting different
     * activities based on actor type
     */
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


    /**
     * This method returns whether the actor has been successfully created
     * @return if actor has been successfully created
     */
    public boolean CreateActor(){
        return ActorCreated;
    }



    public int getActorID() {return ActorID;}
    public int getActorType() {return ActorType;}
    public String getErrorMessage() {return ErrorMessage;}
}
