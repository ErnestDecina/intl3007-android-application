package com.ernestjohndecina.intl3007_diary_application.layers.data_layer;


import android.app.Activity;
import android.util.Log;

import androidx.room.Room;

import com.ernestjohndecina.intl3007_diary_application.database.DiaryDatabase;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

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

    String root;

    static Long id = 0L;


    /*
     * DataLayer Constructor
     */
    public DataLayer(
            Activity mainActivity,
            ExecutorService executorService
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        initRoomDatabase();
        root = mainActivity.getExternalFilesDir("").getAbsolutePath();
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
            String location,
            String last_update,
            Integer mood,
            ArrayList<byte[]> encryptedBitmapArrayList,
            byte[] encryptedAudio
    ) {
        // Creating Diary Entry
        DiaryEntry newDiaryEntry = new DiaryEntry();
        newDiaryEntry.title = title;
        newDiaryEntry.content = content;
        newDiaryEntry.timestamp = timestamp;
        newDiaryEntry.numImages = encryptedBitmapArrayList.size();
        // newDiaryEntry.numAudio = encryptedAudioArrayList.size();
        newDiaryEntry.location = location;
        newDiaryEntry.LastUpdate = last_update;
        newDiaryEntry.mood = mood;

        // Store dairy
        id = diaryDatabase.diaryEntryDao().insertDiaryEntry(newDiaryEntry);

        // Write image to file storage
        int j = 0;
        for (byte[] encryptedBitmap : encryptedBitmapArrayList) {
            int finalJ = j;
            executorService.submit(() -> {
                String rootImages = root + "/entries/" + id + "/images";
                File rootFolder = new File(rootImages);
                rootFolder.mkdirs();

                File image = new File(rootImages, finalJ + "_image.bin");

                try {
                    image.createNewFile();
                    FileOutputStream stream = new FileOutputStream(image);
                    stream.write(encryptedBitmap);
                    stream.close();
                } catch (IOException e) {
                    Log.d("TEST", e.toString());
                    throw new RuntimeException(e);
                }
            });

            j++;
        } // End foreach


        // Write Audio
        executorService.submit(() -> {
            String rootImages = root + "/entries/" + id + "/audio";
            File rootFolder = new File(rootImages);
            rootFolder.mkdirs();

            File image = new File(rootImages,  "audio.bin");

            try {
                image.createNewFile();
                FileOutputStream stream = new FileOutputStream(image);
                stream.write(encryptedAudio);
                stream.close();
            } catch (IOException e) {
                Log.d("TEST", e.toString());
                throw new RuntimeException(e);
            }
        });
    } // End writeDiaryEntry()


    /**
     *
     */
    public Future<List<DiaryEntry>> readAllDiaryEntry() {
        return executorService.submit(() -> {
            List<DiaryEntry> diaryEntries = diaryDatabase.diaryEntryDao().selectAllDiaryEntry();
            return diaryEntries;
        });
    }

    public Future<byte[]> readDiaryEntryAudio(
            Long id
    ) {
        return executorService.submit(() -> {
            try {
                String rootImages = root + "/entries/" + id + "/audio";
                File audio = new File(rootImages + "/audio.bin");
                return Files.readAllBytes(audio.toPath());
            } catch (Exception ignored) {
                return null;
            }
        });
    }

    public Future<List<byte[]>> readDiaryEntryImages(
            Long id,
            Integer numImages
    ) {
        return executorService.submit(() -> {
            List<byte[]> encryptedBitmaps = new ArrayList<>();
            String rootImages = root + "/entries/" + id + "/images";

            for(int i = 0; i < numImages; i++) {
                File image = new File(rootImages + "/" + i + "_image.bin");
                Log.d("TEST", String.valueOf(image.toPath()));
                byte[] imageFile = Files.readAllBytes(image.toPath());
                encryptedBitmaps.add(imageFile);
            } // End for loop

            return encryptedBitmaps;
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
