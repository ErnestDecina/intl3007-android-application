package com.ernestjohndecina.intl3007_diary_application.layers.security_layer;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.data_layer.DataLayer;
import com.ernestjohndecina.intl3007_diary_application.utilites.security.Crypt;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SecurityLayer {
    // Activity
    Activity mainActivity;
    ExecutorService executorService;
    Crypt crypt = new Crypt();

    // DataLayer
    DataLayer dataLayer;


    /**
     * SecurityLayer Constructor
     */
    public SecurityLayer(
            Activity mainActivity,
            ExecutorService executorService
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        createDataLayer();
    }


    void createDataLayer() {
        this.dataLayer = new DataLayer(mainActivity, executorService);
    }


    /**
     *
     * @param username
     * @param password
     */
    public void encryptUserDetails(
            String username,
            String password
    ){
        String encryptedUsername = crypt.encryptString(username);
        String encryptedPassword = crypt.encryptString(password);

        dataLayer.writeUserDetails(encryptedUsername, encryptedPassword);
    }


    /**
     *
     * @return
     */
    public User decryptUserDetails() throws ExecutionException, InterruptedException {
        Future<User> user = dataLayer.readUserDetails();
        User encryptedUser = user.get();

        User decryptedUser = new User();
        decryptedUser.username = crypt.decryptString(encryptedUser.username);
        decryptedUser.password = crypt.decryptString(encryptedUser.password);

        return decryptedUser;
    }



}
