package com.example.cyhelp;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.view.View;

import junit.framework.TestCase;

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
    public void getActorInfoTest() {
        SignUpModel testModel = mock(SignUpModel.class);
        SignUpActivity testActivity = mock(SignUpActivity.class);
        View v = mock(View.class);

        when(testModel.getUserID()).thenReturn(1);
        when(testModel.getUserType()).thenReturn(4);
        when(testModel.getErrorMessage()).thenReturn("Invalid User Type");

        doNothing().when(testModel).httpCreateUserRequest(isA(JSONObject.class), isA(SignUpPresenter.class));

        SignUpPresenter presenter = new SignUpPresenter(v, testModel, testActivity);
        presenter.getActorInfo();
        Assert.assertEquals("Invalid User Type", presenter.getErrorMessage());
    }

}