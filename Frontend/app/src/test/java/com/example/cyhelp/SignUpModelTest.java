//package com.example.cyhelp;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import java.util.Random;
//
//public class SignUpModelTest {
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//
//    @Test
//    public void signupResponseTest_returnsEmpty() throws JSONException {
//
//        SignUpPresenter test = mock(SignUpPresenter.class);
//        SignUpActivity testActivity = mock(SignUpActivity.class);
//
//        SignUpModel model = new SignUpModel(testActivity.getApplicationContext());
//
//        Random random = new Random();
//
//        String usernameCorrect = "nsandeen" + random.nextInt(2099999999);
//        String firstNameCorrect = "Nick";
//        String lastNameCorrect = "Sandeen";
//        String passwordCorrect = "Password";
//        String addressCorrect = "Ames";
//        int ActorType = 0;
//
//        JSONObject testObject = new JSONObject();
//        testObject.put("username", usernameCorrect);
//        testObject.put("firstName", firstNameCorrect);
//        testObject.put("lastName", lastNameCorrect);
//        testObject.put("password", passwordCorrect);
//        testObject.put("homeAddress", addressCorrect);
//        testObject.put("userType", ActorType);
//
//        doNothing().when(test).SignUpUser(usernameCorrect, firstNameCorrect, lastNameCorrect, passwordCorrect, addressCorrect, ActorType);
//
//        model.httpCreateUserRequest(testObject, test);
//
//        verify(test, times(1)).getActorInfo();
//
//        String ErrorMessage = model.getErrorMessage();
//        assertEquals("", ErrorMessage);
//    }
//
//    @Test
//    public void signupResponseTest_returnsErrorUserAlreadyTaken() throws JSONException {
//
//        SignUpPresenter test = mock(SignUpPresenter.class);
//        SignUpActivity testActivity = mock(SignUpActivity.class);
//
//        SignUpModel model = new SignUpModel(testActivity.getApplicationContext());
//
//        String usernameCorrect = "nsandeen";
//        String firstNameCorrect = "Nick";
//        String lastNameCorrect = "Sandeen";
//        String passwordCorrect = "Password";
//        String addressCorrect = "Ames";
//        int ActorType = 0;
//
//        JSONObject testObject = new JSONObject();
//        testObject.put("username", usernameCorrect);
//        testObject.put("firstName", firstNameCorrect);
//        testObject.put("lastName", lastNameCorrect);
//        testObject.put("password", passwordCorrect);
//        testObject.put("homeAddress", addressCorrect);
//        testObject.put("userType", ActorType);
//
//        doNothing().when(test).SignUpUser(usernameCorrect, firstNameCorrect, lastNameCorrect, passwordCorrect, addressCorrect, ActorType);
//
//        model.httpCreateUserRequest(testObject, test);
//
//        SignUpModel model2 = new SignUpModel(testActivity.getApplicationContext());
//        model2.httpCreateUserRequest(testObject, test);
//
//        verify(test, times(1)).getActorInfo();
//
//        String ErrorMessage = model2.getErrorMessage();
//        //Username is expected to be taken. The error is actually a JSON Parse Error so it could be caused by other things if the presenter does not behave as expected.
//        assertEquals("Username already exists! Please enter a new username.", ErrorMessage);
//    }
//
//    @Test
//    public void signupResponseTest_returnsErrorInvalidUserType() throws JSONException {
//
//        SignUpPresenter test = mock(SignUpPresenter.class);
//        SignUpActivity testActivity = mock(SignUpActivity.class);
//
//        SignUpModel model = new SignUpModel(testActivity.getApplicationContext());
//
//        String usernameCorrect = "nsandeen";
//        String firstNameCorrect = "Nick";
//        String lastNameCorrect = "Sandeen";
//        String passwordCorrect = "Password";
//        String addressCorrect = "Ames";
//        int ActorType = 5;
//
//        JSONObject testObject = new JSONObject();
//        testObject.put("username", usernameCorrect);
//        testObject.put("firstName", firstNameCorrect);
//        testObject.put("lastName", lastNameCorrect);
//        testObject.put("password", passwordCorrect);
//        testObject.put("homeAddress", addressCorrect);
//        testObject.put("userType", ActorType);
//
//        doNothing().when(test).SignUpUser(usernameCorrect, firstNameCorrect, lastNameCorrect, passwordCorrect, addressCorrect, ActorType);
//
//        model.httpCreateUserRequest(testObject, test);
//
//        verify(test, times(1)).getActorInfo();
//
//        String ErrorMessage = model.getErrorMessage();
//        //Parse Error Expected as userType of 5 does not exist.
//        assertEquals("Username already exists! Please enter a new username.", ErrorMessage);
//    }
//
//    @Test
//    public void signupResponseTest_returnsValidUserInfo() throws JSONException {
//
//        SignUpPresenter test = mock(SignUpPresenter.class);
//        SignUpActivity testActivity = mock(SignUpActivity.class);
//
//        SignUpModel model = new SignUpModel(testActivity.getApplicationContext());
//
//        String usernameCorrect = "nsandeen";
//        String firstNameCorrect = "Nick";
//        String lastNameCorrect = "Sandeen";
//        String passwordCorrect = "Password";
//        String addressCorrect = "Ames";
//        int ActorType = 5;
//
//        JSONObject testObject = new JSONObject();
//        testObject.put("username", usernameCorrect);
//        testObject.put("firstName", firstNameCorrect);
//        testObject.put("lastName", lastNameCorrect);
//        testObject.put("password", passwordCorrect);
//        testObject.put("homeAddress", addressCorrect);
//        testObject.put("userType", ActorType);
//
//        doNothing().when(test).SignUpUser(usernameCorrect, firstNameCorrect, lastNameCorrect, passwordCorrect, addressCorrect, ActorType);
//
//        model.httpCreateUserRequest(testObject, test);
//
//        verify(test, times(1)).getActorInfo();
//
//        String ErrorMessage = model.getErrorMessage();
//        //Parse Error Expected as userType of 5 does not exist.
//        assertEquals("Username already exists! Please enter a new username.", ErrorMessage);
//    }
//
//    @Test
//    public void sampleTest() {
//        assertEquals(2,2);
//    }
//
//
//
//
//
//
//}