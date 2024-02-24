package com.ernestjohndecina.intl3007_diary_application.layers.system_features;

import android.app.Activity;
import java.util.concurrent.ExecutorService;

import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.DiaryFeatures;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.UserFeatures;

public class SystemFeatures {
    Activity mainActivity;
    ExecutorService executorService;


    // Security Layer
    SecurityLayer securityLayer;


    // Features
    public UserFeatures userFeatures;
    public DiaryFeatures diaryFeatures;



    /**
     * SystemFeatures Constructor
     */
    public SystemFeatures(
            Activity mainActivity,
            ExecutorService executorService
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        initSecurityLayer();
        createFeatureLayers();
    }


    private void initSecurityLayer() {
        this.securityLayer = new SecurityLayer(mainActivity, executorService);
    }

    private void createFeatureLayers() {
        this.userFeatures = new UserFeatures(mainActivity, executorService, securityLayer);
        this.diaryFeatures = new DiaryFeatures(mainActivity, executorService, securityLayer);
    }
}
