package com.ernestjohndecina.intl3007_diary_application;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.UserFeatures;

import java.util.concurrent.ExecutionException;

public class UserFeaturesUnitTest {
    @Mock
    private SecurityLayer mockSecurityLayer;

    private UserFeatures userFeatures;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userFeatures = new UserFeatures(null, null, mockSecurityLayer);
    }

    @Test
    public void testCreateUserAccount() {
        userFeatures.createUserAccount("John", "john@example.com", "john123", "1234");
        verify(mockSecurityLayer).encryptUserDetails("John", "john@example.com", "john123", "1234");
    }

    @Test
    public void testGetUserAccountDetails() throws ExecutionException, InterruptedException {
        User mockUser = new User();
        mockUser.userID = 1;
        mockUser.firstName = "John";
        mockUser.email = "john@example.com";
        mockUser.username = "john123";
        mockUser.pin = "1234";

        when(mockSecurityLayer.decryptUserDetails()).thenReturn(mockUser);
        User user = userFeatures.getUserAccountDetails();
        assertEquals(mockUser, user);
    }

}
