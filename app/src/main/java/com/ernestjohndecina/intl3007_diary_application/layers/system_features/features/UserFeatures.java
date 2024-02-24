package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

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
            String username,
            String password
    ) {
        securityLayer.encryptUserDetails(username, password);
    }


    public User getUserAccountDetails() {
        try {
            return securityLayer.decryptUserDetails();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
