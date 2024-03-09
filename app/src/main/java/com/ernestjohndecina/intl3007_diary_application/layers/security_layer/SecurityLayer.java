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


    public void encryptDiaryEntry(
            String title,
            String content,
            String timestamp,
            String image_url,
            String Voice_Rec_url,
            String location,
            String last_update
    ) {
        String encryptedTitle =       crypt.encryptString(title);
        String encryptedContent =     crypt.encryptString(content);
        String encryptedTimestamp =   crypt.encryptString(timestamp);
        String encryptedImageUrl =    crypt.encryptString(image_url);
        String encryptedVoiceRecUrl = crypt.encryptString(Voice_Rec_url);
        String encryptedLocation =    crypt.encryptString(location);
        String encryptedLastUpdate =  crypt.encryptString(last_update);

        dataLayer.writeDiaryEntry(
                encryptedTitle,
                encryptedContent,
                encryptedTimestamp,
                encryptedImageUrl,
                encryptedVoiceRecUrl,
                encryptedLocation,
                encryptedLastUpdate
        );
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
        Future<User> user = dataLayer.readUserDetails();
        User encryptedUser = user.get();

        User decryptedUser = new User();
        decryptedUser.firstName = crypt.decryptString(encryptedUser.firstName);
        decryptedUser.email = crypt.decryptString(encryptedUser.email);
        decryptedUser.username = crypt.decryptString(encryptedUser.username);
        decryptedUser.pin = crypt.decryptString(encryptedUser.pin);

        return decryptedUser;
    }



}
