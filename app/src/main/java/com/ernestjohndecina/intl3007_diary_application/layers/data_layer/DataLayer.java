package com.ernestjohndecina.intl3007_diary_application.layers.data_layer;


import android.app.Activity;
import android.util.Log;

import androidx.room.Room;

import com.ernestjohndecina.intl3007_diary_application.database.DiaryDatabase;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
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
     *  Write Diary Entry
     */
    public void writeDiaryEntry(

            String title,
            String content,
            String timestamp,
            String image_url,
            String Voice_Rec_url,
            String location,
            String last_update

    ) {
        DiaryEntry newDiaryEntry = new DiaryEntry();
        newDiaryEntry.title = title;
        newDiaryEntry.content = content;
        newDiaryEntry.timestamp = timestamp;
        newDiaryEntry.imageUrl = image_url;
        newDiaryEntry.VoiceRecUrl= Voice_Rec_url;
        newDiaryEntry.location = location;
        newDiaryEntry.LastUpdate = last_update;



        executorService.submit(() -> {
           diaryDatabase.diaryEntryDao().insertDiaryEntry(newDiaryEntry);
        });
    }

    /**
     *  Store user login details
     */
    public void writeUserDetails(
            String firstName,
            String email,
            String username,
            String pin

    ) {
        User newUser = new User();
        newUser.firstName = firstName;
        newUser.email = email;
        newUser.username = username;
        newUser.pin = pin;

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
            if(users.size() == 0) return null;

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
