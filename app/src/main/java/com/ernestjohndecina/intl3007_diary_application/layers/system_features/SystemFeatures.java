package com.ernestjohndecina.intl3007_diary_application.layers.system_features;

import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

public class SystemFeatures {
    // Security Layer
    SecurityLayer securityLayer = new SecurityLayer();

    /**
     * SystemFeatures Constructor
     */
    public SystemFeatures() {

    }

    /**
     * Adds a Diary Entry
     *
     * @param date
     * @param diaryMessage
     */
    public void addDiaryEntry(
            String date,
            String diaryMessage
    ) {

        securityLayer.storeEncryptedDiaryEntry(date, diaryMessage);
        securityLayer.storeEncryptedImage();
    }

}
