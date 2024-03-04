package com.ernestjohndecina.intl3007_diary_application.layers.system_features;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;

import com.ernestjohndecina.intl3007_diary_application.MainActivity;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.DiaryFeatures;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.FileFeatures;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.UserFeatures;

public class SystemFeatures {
    MainActivity mainActivity;
    ExecutorService executorService;


    // Security Layer
    SecurityLayer securityLayer;


    // Features
    public UserFeatures userFeatures;
    public DiaryFeatures diaryFeatures;
    public FileFeatures fileFeatures;



    /**
     * SystemFeatures Constructor
     */
    public SystemFeatures(
            MainActivity mainActivity,
            ExecutorService executorService
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        initSecurityLayer();
        createFeatureLayers();
    }


    protected SystemFeatures() {
    }


    private void initSecurityLayer() {
        this.securityLayer = new SecurityLayer(mainActivity, executorService);
    }

    private void createFeatureLayers() {
        this.userFeatures = new UserFeatures(mainActivity, executorService, securityLayer);
        this.diaryFeatures = new DiaryFeatures(mainActivity, executorService, securityLayer);
        this.fileFeatures = new FileFeatures(mainActivity, executorService);
    }
}
