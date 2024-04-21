package com.ernestjohndecina.intl3007_diary_application.layers.security_layer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.data_layer.DataLayer;
import com.ernestjohndecina.intl3007_diary_application.utilites.security.Crypt;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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

    static Boolean loginState = false;
    public static Integer userId = 0;


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


    public void encryptDiaryEntry(
            Integer userId,
            String title,
            String content,
            String timestamp,
            String location,
            String last_update,
            Integer mood,
            ArrayList<Bitmap> bitmapArrayList
    ) {
        ArrayList<byte[]> encryptedBitmapArrayList = new ArrayList<>();

        // Encrypt Strings
        String encryptedTitle =       crypt.encryptString(title);
        String encryptedContent =     crypt.encryptString(content);
        String encryptedTimestamp =   crypt.encryptString(timestamp);
        String encryptedLocation =    crypt.encryptString(location);
        String encryptedLastUpdate =  crypt.encryptString(last_update);

        // Encrypt Images
        for (Bitmap bitmap : bitmapArrayList) {
            byte[] encryptedImage = crypt.encryptImage(bitmap);
            encryptedBitmapArrayList.add(encryptedImage);
        } // End foreach

        // Encrypt Audio
        String fileName = mainActivity.getExternalCacheDir().getAbsolutePath();
        fileName += "/create_audio.3gp";

        File audioFile = new File(fileName);
        byte[] encryptedAudio = crypt.encryptAudio(audioFile);

        // Delete Cache
        audioFile.delete();


        // Write Diary Entry
        dataLayer.writeDiaryEntry(
                userId,
                encryptedTitle,
                encryptedContent,
                encryptedTimestamp,
                encryptedLocation,
                encryptedLastUpdate,
                mood,
                encryptedBitmapArrayList,
                encryptedAudio
        );
    }

    /**
     *
     */
    public List<DiaryEntry> decryptAllDiaryEntry() throws ExecutionException, InterruptedException {
        Future<List<DiaryEntry>> encryptedDiaryEntryFuture = dataLayer.readAllDiaryEntry();
        List<DiaryEntry> encryptedDiaryEntryList = encryptedDiaryEntryFuture.get();

        // Decrypt
        for (DiaryEntry encryptedDiaryEntry: encryptedDiaryEntryList) {
            // Decrypt Content
            encryptedDiaryEntry.title =      crypt.decryptString(encryptedDiaryEntry.title);
            encryptedDiaryEntry.content =    crypt.decryptString(encryptedDiaryEntry.content);
            encryptedDiaryEntry.timestamp =  crypt.decryptString(encryptedDiaryEntry.timestamp);
            encryptedDiaryEntry.LastUpdate = crypt.decryptString(encryptedDiaryEntry.LastUpdate);
            encryptedDiaryEntry.location =   crypt.decryptString(encryptedDiaryEntry.location);

        } // ENd for each

        return encryptedDiaryEntryList;
    } // End decryptDiaryEntry


    public ArrayList<Bitmap> decryptImages(DiaryEntry entry) {
        try {
            ArrayList<Bitmap> decryptedImages = new ArrayList<>();
            Long id = entry.entryID;
            Integer numImages = entry.numImages;

            Future<List<byte[]>> encryptedBitmapListFuture = dataLayer.readDiaryEntryImages(id, numImages);
            List<byte[]> encryptedBitmapList = encryptedBitmapListFuture.get();

            // Decrypt
            for (byte[] encryptedBitmap: encryptedBitmapList) {
                decryptedImages.add(crypt.decryptImage(encryptedBitmap));
            } // End foreach


            return decryptedImages;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    } // End decryptImages

    public byte[] decryptAudio(DiaryEntry entry) {
        try {
            Long id = entry.entryID;
            Future<byte[]> encryptedAudioFuture = dataLayer.readDiaryEntryAudio(id);
            byte[] encryptedAudio = encryptedAudioFuture.get();

            if(encryptedAudio == null) {
                return null;
            }

            // Decrypt Audio
            return crypt.decryptAudio(encryptedAudio);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param username
     * @param pin
     */
    public void encryptUserDetails(
            String firstName,
            String email,
            String username,
            String pin
    ){
        String encryptedFirstName = crypt.encryptString(firstName);
        String encryptedEmail = crypt.encryptString(email);
        String encryptedUsername = crypt.encryptString(username);
        String encryptedPin = crypt.encryptString(pin);

        dataLayer.writeUserDetails(
                encryptedFirstName,
                encryptedEmail,
                encryptedUsername,
                encryptedPin
        );
    }


    /**
     *
     * @return
     */
    public User decryptUserDetails() throws ExecutionException, InterruptedException {
        Future<User> user = dataLayer.readUserDetails(SecurityLayer.userId);
        User encryptedUser = user.get();

        if (encryptedUser == null) return null;

        User decryptedUser = new User();
        decryptedUser.firstName = crypt.decryptString(encryptedUser.firstName);
        decryptedUser.email = crypt.decryptString(encryptedUser.email);
        decryptedUser.username = crypt.decryptString(encryptedUser.username);
        decryptedUser.pin = crypt.decryptString(encryptedUser.pin);

        return decryptedUser;
    }

    public ArrayList<User> decryptUserAllLoginDetails() throws ExecutionException, InterruptedException {
        Future<List<User>> user = dataLayer.readAllUserDetails();
        List<User> encryptedUsers = user.get();
        ArrayList<User> decryptedUsers = new ArrayList<>();

        if (encryptedUsers == null) return null;

        for(User encryptedUser: encryptedUsers) {
            User decryptedUser = new User();
            decryptedUser.userID = encryptedUser.userID;
            decryptedUser.username = crypt.decryptString(encryptedUser.username);
            decryptedUser.pin = crypt.decryptString(encryptedUser.pin);

            decryptedUsers.add(decryptedUser);
        }



        return decryptedUsers;
    }


    public void setLoginStateTrue() {
        loginState = true;
    }

    public void setLoginStateFalse() {
        loginState = false;
    }

    public Boolean getLoginState() {
        return loginState;
    }

    public void setUserId(Integer userId) {
        SecurityLayer.userId = userId;
    }
    public Integer getUserId() { return userId; }




}
