package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

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
            User correctUserDetails = securityLayer.decryptUserDetails();

            if(!Objects.equals(correctUserDetails.username, username)) return Boolean.FALSE;
            if(!Objects.equals(correctUserDetails.pin, pin)) return Boolean.FALSE;

            return Boolean.TRUE;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
