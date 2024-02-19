package com.ernestjohndecina.intl3007_diary_application.layers.security_layer;

import com.ernestjohndecina.intl3007_diary_application.layers.data_layer.DataLayer;

public class SecurityLayer {
    // DataLayer
    DataLayer dataLayer = new DataLayer();


    /**
     * SecurityLayer Constructor
     */
    public SecurityLayer() {

    }

    /**
     *
     */
    public void storeEncryptedDiaryEntry(
            String date,
            String diaryMessage
    ) {
        dataLayer.storeDiaryMessage(date, diaryMessage);
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
