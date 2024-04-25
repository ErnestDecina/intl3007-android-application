package com.ernestjohndecina.intl3007_diary_application;

import android.app.Activity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.mockito.Mockito;
import android.util.Log;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.UserFeatures;

public class UserFeaturesUnitTest {
    @Mock
    private Activity mainActivity;
    @Mock
    private ExecutorService executorService;
    @Mock
    private SecurityLayer securityLayer;

    private UserFeatures userFeatures;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userFeatures = new UserFeatures(mainActivity, executorService, securityLayer);
    }

    @Test
    public void testCreateUserAccount() {
        userFeatures.createUserAccount("John", "john@example.com", "john123", "1234");
        verify(securityLayer).encryptUserDetails("John", "john@example.com", "john123", "1234");
    }

    @Test
    public void testGetUserAccountDetails() throws Exception {
        User mockUser = new User();
        when(securityLayer.decryptUserDetails()).thenReturn(mockUser);
        User result = userFeatures.getUserAccountDetails();
        verify(securityLayer).decryptUserDetails();
        assert result == mockUser;
    }

    @Test
    public void testValidateUserCorrectCredentials() throws Exception {
        try (MockedStatic<Log> mockedLog = Mockito.mockStatic(Log.class)) {
            ArrayList<User> users = new ArrayList<>();
            User mockUser = new User();
            mockUser.username = "john123";
            mockUser.pin = "1234";
            users.add(mockUser);

            when(securityLayer.decryptUserAllLoginDetails()).thenReturn(users);

            Assert.assertTrue(userFeatures.validateUser("john123", "1234"));
        }
    }

    @Test
    public void testValidateUserIncorrectPin() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        User mockUser = new User();
        mockUser.username = "john123";
        mockUser.pin = "1234";
        users.add(mockUser);

        when(securityLayer.decryptUserAllLoginDetails()).thenReturn(users);
        assertFalse(userFeatures.validateUser("john123", "0000"));
    }

    @Test
    public void testValidateUserIncorrectUsername() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        User mockUser = new User();
        mockUser.username = "john123";
        mockUser.pin = "1234";
        users.add(mockUser);

        when(securityLayer.decryptUserAllLoginDetails()).thenReturn(users);
        assertFalse(userFeatures.validateUser("jane123", "1234"));
    }

    @Test
    public void testCheckUserExists() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        when(securityLayer.decryptUserAllLoginDetails()).thenReturn(users);
        assert !userFeatures.checkUserExists();

        users.add(new User());
        when(securityLayer.decryptUserAllLoginDetails()).thenReturn(users);
        assert userFeatures.checkUserExists();
    }

    @Test
    public void testCheckUserLoggedIn() {
        when(securityLayer.getLoginState()).thenReturn(true);
        assert userFeatures.checkUserLoggedIn();
        when(securityLayer.getLoginState()).thenReturn(false);
        assert !userFeatures.checkUserLoggedIn();
    }
}
