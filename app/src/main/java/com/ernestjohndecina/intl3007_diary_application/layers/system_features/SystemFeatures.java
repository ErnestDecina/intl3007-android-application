package com.ernestjohndecina.intl3007_diary_application.layers.system_features;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

public class SystemFeatures {
    // Activity
    Activity mainActivity;

    // Security Layer
    SecurityLayer securityLayer;

    /**
     * SystemFeatures Constructor
     */
    public SystemFeatures(
            Activity mainActivity
    ) {
        this.mainActivity = mainActivity;
        this.securityLayer = new SecurityLayer(mainActivity);
    }

    /**
     * Adds User Details
     *
     * @param username
     * @param password
     */
    public void addUserDetails(
            String username,
            String password
    ) {

        securityLayer.storeEncryptedDiaryEntry(username, password);
        securityLayer.storeEncryptedImage();
    }

}
