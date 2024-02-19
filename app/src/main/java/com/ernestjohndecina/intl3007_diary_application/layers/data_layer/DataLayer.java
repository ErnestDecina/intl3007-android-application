package com.ernestjohndecina.intl3007_diary_application.layers.data_layer;


import android.util.Log;

/**
 * This Class will interface with the Room Database & the file storage
 */
public class DataLayer {

    /**
     * DataLayer Constructor
     */
    public DataLayer() {

    }

    /**
     *
     */
    public void storeDiaryMessage(
            String encryptedDate,
            String encryptedDiaryMessage
    ) {
        Log.d("TEST", encryptedDate + " " + encryptedDiaryMessage);
    }

    /**
     * Stores Encrypted Images into the storage
     * @
     */
    public void storeImage() {

    }

    /**
     * Reads Encrypted Images from storage
     */
    public void readImage() {

    }

    /**
     * Stores Encrypted Audio into the storage
     */
    public void storeAudio() {

    }

    /**
     * Reads Encrypted Audio from storage
     */
    public void readAudio() {

    }

    /**
     * Stores user login details
     */
    public void storeUserDetails() {

    }

    /**
     * Reads user login details
     */
    public void readUserDetails() {

    }

}
