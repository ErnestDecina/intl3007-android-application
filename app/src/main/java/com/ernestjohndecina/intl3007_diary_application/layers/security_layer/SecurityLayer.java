package com.ernestjohndecina.intl3007_diary_application.layers.security_layer;

import android.app.Activity;
import android.util.Log;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.data_layer.DataLayer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SecurityLayer {
    // Activity
    Activity mainActivity;

    // DataLayer
    DataLayer dataLayer;


    /**
     * SecurityLayer Constructor
     */
    public SecurityLayer(
            Activity mainActivity
    ) {
        this.mainActivity = mainActivity;
        this.dataLayer = new DataLayer(mainActivity);
    }

    /**
     *
     */
    public void storeEncryptedDiaryEntry(
            String username,
            String password
    ) {
        dataLayer.storeUserDetails(username, password);




        Future<User> user = dataLayer.readUserDetails();

    }

    /**
     * Stores Encrypted Images into the storage
     * @
     */
    public void storeEncryptedImage() {

    }

    /**
     * Reads Encrypted Images from storage
     */
    public void readDecryptedImage() {

    }

    /**
     * Stores Encrypted Audio into the storage
     */
    public void storeEncryptedAudio() {

    }

    /**
     * Reads Encrypted Audio from storage
     */
    public void readDecryptedAudio() {

    }

    /**
     * Stores Encrypted user login details
     */
    public void storeEncryptedUserDetails() {

    }

    /**
     * Reads Encrypted user login details
     */
    public void readDecryptedUserDetails() {

    }




}
