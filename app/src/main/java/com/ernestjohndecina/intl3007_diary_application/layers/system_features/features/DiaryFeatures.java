package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.app.Activity;

import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

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

}
