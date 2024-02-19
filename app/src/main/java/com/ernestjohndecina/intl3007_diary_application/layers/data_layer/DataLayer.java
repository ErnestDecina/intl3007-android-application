package com.ernestjohndecina.intl3007_diary_application.layers.data_layer;


import android.app.Activity;
import android.util.Log;

import androidx.room.Room;

import com.ernestjohndecina.intl3007_diary_application.database.DiaryDatabase;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This Class will interface with the Room Database & the file storage
 */
public class DataLayer {
    // Activity
    Activity mainActivity;

    // Room Database
    DiaryDatabase diaryDatabase;

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
    public DataLayer(
            Activity mainActivity
    ) {
        this.mainActivity = mainActivity;

        this.diaryDatabase = Room.databaseBuilder(
                mainActivity,
                DiaryDatabase.class,
                "diary-database"
        ).build();
    }

    /**
     *  Store user login details
     */
    public void storeUserDetails(
            String username,
            String password
    ) {
        User newUser = new User();
        newUser.username = username;
        newUser.password = password;

        executorService.submit(() -> {
            Log.d("TEST", "Storing " + newUser.username + " " + newUser.password);
            diaryDatabase.userDAO().insertUser(newUser);
        });
    }

    /**
     * Reads user login details
     *
     * @return
     */
    public Future<User> readUserDetails() {
        return executorService.submit(() -> {
            List<User> users = diaryDatabase.userDAO().selectUser();
            return users.get(0);
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



}
