package com.ernestjohndecina.intl3007_diary_application.layers.data_layer;


import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This Class will interface with the Room Database & the file storage
 */
public class DataLayer {
    // ExecutorService
    ExecutorService executorService = new ThreadPoolExecutor(
            4,
            10,
            5L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>()
    );

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
        executorService.submit(() -> {
            Log.d("TEST", encryptedDate + " " + encryptedDiaryMessage);
        });
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
