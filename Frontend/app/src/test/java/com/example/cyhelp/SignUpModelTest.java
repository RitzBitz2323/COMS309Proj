package com.example.cyhelp;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;

public class SignUpModelTest extends TestCase {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void signupResponseTest_returnsTrue() throws JSONException {

        SignUpPresenter test = mock(SignUpPresenter.class);
        SignUpActivity testActivity = mock(SignUpActivity.class);

        SignUpModel model = new SignUpModel(testActivity.getApplicationContext());

        Random random = new Random();

        String usernameCorrect = "nsandeen" + random.nextInt(2099999999);
        String firstNameCorrect = "Nick";
        String lastNameCorrect = "Sandeen";
        String passwordCorrect = "Password";
        String addressCorrect = "Password";
        int ActorType = 0;

        JSONObject testObject = new JSONObject();
        testObject.put("username", usernameCorrect);
        testObject.put("firstName", firstNameCorrect);
        testObject.put("lastName", lastNameCorrect);
        testObject.put("password", passwordCorrect);
        testObject.put("homeAddress", addressCorrect);
        testObject.put("userType", ActorType);

        doNothing().when(test).SignUpUser(usernameCorrect, firstNameCorrect, lastNameCorrect, passwordCorrect, addressCorrect, ActorType);

        model.httpCreateUserRequest(testObject, test);

        verify(test, times(1)).getActorInfo();

        String ErrorMessage = model.getErrorMessage();
        assertEquals("", ErrorMessage);
    }











}