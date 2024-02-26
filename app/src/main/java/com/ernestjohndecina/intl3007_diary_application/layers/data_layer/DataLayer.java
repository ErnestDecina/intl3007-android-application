package com.ernestjohndecina.intl3007_diary_application.layers.data_layer;


import android.app.Activity;
import android.util.Log;

import androidx.room.Room;

import com.ernestjohndecina.intl3007_diary_application.database.DiaryDatabase;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * This Class will interface with the Room Database & the file storage
 */
public class DataLayer {
    // Activity
    Activity mainActivity;

    // Room Database
    DiaryDatabase diaryDatabase;

    // ExecutorService
    ExecutorService executorService;


    /**
     * DataLayer Constructor
     */
    public DataLayer(
            Activity mainActivity,
            ExecutorService executorService
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        initRoomDatabase();
    }


    void initRoomDatabase() {
        this.diaryDatabase = Room.databaseBuilder(
                mainActivity,
                DiaryDatabase.class,
                "diary-database"
        ).build();
    }


    /**
     *  Store user login details
     */
    public void writeUserDetails(
            String username,
            String password
    ) {
        User newUser = new User();
        newUser.username = username;
        newUser.password = password;

        executorService.submit(() -> {
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
            return users.get(users.size() - 1);
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
