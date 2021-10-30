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
     *
     * @param view
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
                Intent i = new Intent(SignUpActivity.this, TechActivity.class);
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