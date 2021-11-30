package com.example.cyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SignUpActivity takes user input and passes info to the SignUpPresenter in order to create a new actor.
 *
 * @author Nick Sandeen
 */
public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected String actorType;
    protected int actorTypeID;
    private TextView loading;
    protected boolean UsernameAvailable;
    protected int ActorID;
    protected int ActorType;
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_SignUpActivity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userTypes, R.layout.spinner_item_sign_up);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        loading = (TextView) findViewById(R.id.loading_SignUpActivity);
        loading.setVisibility(View.GONE);

        UsernameAvailable = false;
    }

    /**
     * This method is called when the user clicks the Sign Up button
     * It uses all the information entered by the user to initialise a SignUpPresenter object
     * If all the information entered is not empty and the password matches with confirm password,
     * the SignUpPresenter's SignUpUser method is called
     * If the information entered was incorrect, the appropriate error message is displayed as a Toast.
     * @param view the View that was clicked
     */
    public void loginUser(View view) {

        loading.setVisibility(View.VISIBLE);

        EditText username = (EditText) findViewById(R.id.editTextUsername_SignUpActivity);
        EditText firstName = (EditText) findViewById(R.id.editTextFirstName_SignUpActivity);
        EditText lastName = (EditText) findViewById(R.id.editTextLastName_SignUpActivity);
        EditText password = (EditText) findViewById(R.id.editTextPassword_SignUpActivity);
        EditText confirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword_SignUpActivity);
        EditText address = (EditText) findViewById(R.id.editTextTextPostalAddress_SignUpActivity);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_SignUpActivity);

        presenter = new SignUpPresenter(view, view.getContext(), this);

        if (username.getText().toString().length() != 0 &&
                firstName.getText().length() != 0 &&
                lastName.getText().length() != 0 &&
                password.getText().length() != 0 &&
                confirmPassword.getText().length() != 0 &&
                address.getText().length() != 0 &&
                spinner.toString().length() != 0 &&
                password.getText().toString().equals(confirmPassword.getText().toString())) {

            presenter.SignUpUser(username.getText().toString(), firstName.getText().toString(),
                                                        lastName.getText().toString(), password.getText().toString(),
                                                        address.getText().toString(), actorTypeID);
        }
        //User error messages
        else if (username.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter a username!", Toast.LENGTH_SHORT).show();
        } else if (firstName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your first name!", Toast.LENGTH_SHORT).show();
        } else if (lastName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your last name!", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter a password!", Toast.LENGTH_SHORT).show();
        } else if (confirmPassword.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please confirm your password!", Toast.LENGTH_SHORT).show();
        } else if (address.getText().toString().length() == 00) {
            Toast.makeText(getApplicationContext(), "Please enter your home address!", Toast.LENGTH_SHORT).show();
        } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Passwords must match!", Toast.LENGTH_SHORT).show();
        }

        loading.setVisibility(View.GONE);
    }


    /**
     * This method is called after the user's information is sent to the server and their account has been created.
     * Based on the actor type (user, technician, etc.), it starts another activity
     * @param ActorCreated whether the actor has been created
     */
    public void ActorCreated(Boolean ActorCreated){


        if (ActorCreated) {
            ActorID = presenter.getActorID();
            ActorType = presenter.getActorType();

            //System.out.println("Actor Type: " + ActorType);

            loading.setVisibility(View.GONE);

            if (ActorType == 0){
                Intent i = new Intent(SignUpActivity.this, ViewTicketsActivity.class);
                i.putExtra("id", ActorID);
                i.putExtra("userType", ActorType);
                startActivity(i);
            } else if (ActorType == 1) {
                Intent i = new Intent(SignUpActivity.this, TechFilterActivity.class);
                i.putExtra("id", ActorID);
                i.putExtra("userType", ActorType);
                startActivity(i);
            } else if (ActorType == 2) {
                Intent i = new Intent(SignUpActivity.this, CompanyActivity.class);
                i.putExtra("id", ActorID);
                i.putExtra("userType", ActorType);
                startActivity(i);
            } else if (ActorType == 3) {
                Intent i = new Intent(SignUpActivity.this, AdminActivity.class);
                i.putExtra("id", ActorID);
                i.putExtra("userType", ActorType);
                startActivity(i);
            }
        } else {
            //Display reason for failed signup
            loading.setVisibility(View.GONE);
            //System.out.println("HHHHHHHHHHH " + presenter.getErrorMessage());
            //this.setToast(presenter.getErrorMessage());
            Toast.makeText(getApplicationContext(), presenter.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * This method is called when an item is selected from the Category drop down menu
     * It records the item selected so that it can be sent to the server as part of a ticket
     * @param adapterView The AdapterView where the selection happened
     * @param view The View within the AdapterView that was selected
     * @param i The position of the item selected, starting at 0
     * @param l The row ID of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
            actorType = adapterView.getItemAtPosition(i).toString();
            if (actorType.equals("User")) {
                actorTypeID = 0;
            }
            if (actorType.equals("Technician")) {
                actorTypeID = 1;
            }
            if (actorType.equals("Company")) {
                actorTypeID = 2;
            }
            if (actorType.equals("Administrator")) {
                actorTypeID = 3;
            }
        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}