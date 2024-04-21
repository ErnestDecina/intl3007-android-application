package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.app.Activity;
import android.util.Log;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class UserFeatures {
    Activity mainActivity;
    ExecutorService executorService;

    SecurityLayer securityLayer;

    public UserFeatures(
            Activity mainActivity,
            ExecutorService executorService,
            SecurityLayer securityLayer
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        this.securityLayer = securityLayer;
    }

    /**
     *
     * @param username
     * @param password
     */
    public void createUserAccount(
            String firstName,
            String email,
            String username,
            String pin
    ) {
        securityLayer.encryptUserDetails(
                firstName,
                email,
                username,
                pin
        );
    }


    public User getUserAccountDetails() {
        try {
            return securityLayer.decryptUserDetails();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean validateUser(
            String username,
            String pin
    ) {
        try {
            securityLayer.setLoginStateFalse();

            // Find User
            ArrayList<User> allUsers = securityLayer.decryptUserAllLoginDetails();

            for ( User user: allUsers) {
                if(Objects.equals(user.username, username) && Objects.equals(user.pin, pin)) {
                    Log.d("TEST", "" + user.userID);
                    securityLayer.setUserId(user.userID);
                    securityLayer.setLoginStateTrue();


                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void logoutUser() {
        securityLayer.setLoginStateFalse();
        SecurityLayer.userId = -1;
    }

    public Boolean checkUserExists() {
        ArrayList<User> currentUser = null;
        try {
            currentUser = securityLayer.decryptUserAllLoginDetails();

            return currentUser.size() != 0;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public Boolean checkUserLoggedIn() {
        return securityLayer.getLoginState();
    }

}
