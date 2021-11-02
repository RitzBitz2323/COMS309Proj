package com.example.cyhelp;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.view.View;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class SignUpPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getActorInfoTest_errorMessageWhenInvalidUserType() {
        SignUpModel testModel = mock(SignUpModel.class);
        SignUpActivity testActivity = mock(SignUpActivity.class);
        View testView = mock(View.class);

        when(testModel.getUserID()).thenReturn(1);
        when(testModel.getUserType()).thenReturn(4);
        when(testModel.getErrorMessage()).thenReturn("Invalid User Type");

        SignUpPresenter presenter = new SignUpPresenter(testView, testModel, testActivity);
        presenter.getActorInfo();
        Assert.assertEquals("Invalid User Type", presenter.getErrorMessage());
    }

    @Test
    public void getActorInfoTest_actorNotCreatedWhenUserTypeInvalid() {
        SignUpModel testModel = mock(SignUpModel.class);
        SignUpActivity testActivity = mock(SignUpActivity.class);
        View testView = mock(View.class);

        when(testModel.getUserID()).thenReturn(1);
        when(testModel.getUserType()).thenReturn(4);
        when(testModel.getErrorMessage()).thenReturn("Invalid User Type");

        SignUpPresenter presenter = new SignUpPresenter(testView, testModel, testActivity);
        presenter.getActorInfo();
        Assert.assertEquals(false, presenter.CreateActor());
        verify(testActivity, times(1)).ActorCreated(false);
    }

    @Test
    public void signUpUserTest_runsHttpsCreateRequest() throws JSONException {

        SignUpModel testModel = mock(SignUpModel.class);
        SignUpActivity testActivity = mock(SignUpActivity.class);
        View testView = mock(View.class);
        JSONObject testJSONObject = mock(JSONObject.class);

        when(testJSONObject.put(anyString(), anyString())).thenReturn(new JSONObject());
        when(testJSONObject.put(anyString(), anyInt())).thenReturn(new JSONObject());

        SignUpPresenter presenter = new SignUpPresenter(testView, testModel, testActivity);
        presenter.SignUpUser("p2048", "parthiv", "ganguly", "hello", "ISU", 1, testJSONObject);

        verify(testModel, times(1)).httpCreateUserRequest(isA(JSONObject.class), isA(SignUpPresenter.class));

    }


}