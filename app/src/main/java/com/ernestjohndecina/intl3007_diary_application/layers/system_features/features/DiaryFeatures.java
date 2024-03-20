package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class DiaryFeatures {
    Activity mainActivity;
    ExecutorService executorService;

    SecurityLayer securityLayer;

    public DiaryFeatures(
        Activity mainActivity,
        ExecutorService executorService,
        SecurityLayer securityLayer
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        this.securityLayer = securityLayer;
    }

    public void createDiaryEntry(
            String title,
            String content,
            String timestamp,
            String image_url,
            String Voice_Rec_url,
            String location,
            String last_update,
            ArrayList<URI> images
    ) {
        securityLayer.encryptDiaryEntry(
                title,
                content,
                timestamp,
                image_url,
                Voice_Rec_url,
                location,
                last_update
        );
    }

}
