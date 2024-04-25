package com.ernestjohndecina.intl3007_diary_application;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.data_layer.DataLayer;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;
import com.ernestjohndecina.intl3007_diary_application.utilites.security.Crypt;


import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class SecurityLayerUnitTest {

    @Mock
    private Crypt mockCrypt;
    @Mock
    private DataLayer mockDataLayer;
    @Mock
    private Future<User> mockFuture;

    @Mock
    private ExecutorService mockExecutorService;
    // Assume that you've a mock User class somewhere in your tests

    private SecurityLayer securityLayer;
    private Activity mockActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Create a mock Activity using Robolectric
        ActivityController<Activity> controller = Robolectric.buildActivity(Activity.class);
        mockActivity = controller.get();

        securityLayer = new SecurityLayer(mockActivity, mockExecutorService);
        securityLayer.crypt = mockCrypt;
        securityLayer.dataLayer = mockDataLayer;
    }

    @Test
    public void encryptUserDetails() {
        // Arrange
        when(mockCrypt.encryptString(anyString())).thenReturn("encrypted");

        // Act
        securityLayer.encryptUserDetails("John", "john@example.com", "johnny", "1234");

        // Assert
        verify(mockCrypt).encryptString("John");
        verify(mockCrypt).encryptString("john@example.com");
        verify(mockCrypt).encryptString("johnny");
        verify(mockCrypt).encryptString("1234");
        verify(mockDataLayer).writeUserDetails("encrypted", "encrypted", "encrypted", "encrypted");
    }

    @Test
    public void decryptUserDetails() throws ExecutionException, InterruptedException {
        // Arrange
        User mockUser = new User(); // Assume User has a constructor and setters
        mockUser.firstName = "encryptedName";
        mockUser.email = "encryptedEmail";
        mockUser.username = "encryptedUsername";
        mockUser.pin = "encryptedPin";

        when(mockDataLayer.readUserDetails(anyInt())).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(mockUser);
        when(mockCrypt.decryptString(anyString())).thenReturn("decrypted");

        // Act
        User result = securityLayer.decryptUserDetails();

        // Assert
        assertNotNull(result);
        assertEquals("decrypted", result.firstName);
        assertEquals("decrypted", result.email);
        assertEquals("decrypted", result.username);
        assertEquals("decrypted", result.pin);
        verify(mockCrypt).decryptString("encryptedName");
        verify(mockCrypt).decryptString("encryptedEmail");
        verify(mockCrypt).decryptString("encryptedUsername");
        verify(mockCrypt).decryptString("encryptedPin");
    }

    @Test
    public void testLoginStateManagement() {
        // Act & Assert
        assertFalse(securityLayer.getLoginState());

        securityLayer.setLoginStateTrue();
        assertTrue(securityLayer.getLoginState());

        securityLayer.setLoginStateFalse();
        assertFalse(securityLayer.getLoginState());
    }
}

