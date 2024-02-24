package com.ernestjohndecina.intl3007_diary_application.layers.security_layer;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.data_layer.DataLayer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SecurityLayer {
    // Activity
    Activity mainActivity;
    ExecutorService executorService;

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
        dataLayer.writeUserDetails(username, password);
    }


    /**
     *
     * @return
     */
    public User decryptUserDetails() throws ExecutionException, InterruptedException {
        Future<User> user = dataLayer.readUserDetails();
        return user.get();
    }


    /**
     *
     * @param username
     * @param password
     * @return
     */
    public Boolean validateUser(
            String username,
            String password
    ) {
        return null;
    }



}
