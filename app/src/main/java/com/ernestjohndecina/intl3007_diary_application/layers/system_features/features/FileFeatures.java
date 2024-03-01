package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.app.Activity;
import android.graphics.Bitmap;

import com.ernestjohndecina.intl3007_diary_application.MainActivity;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.file_features.ImageFeatures;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class FileFeatures {
    MainActivity mainActivity;
    ExecutorService executorService;


    public ImageFeatures imageFeatures;



    public FileFeatures(
            MainActivity mainActivity,
            ExecutorService executorService
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;

        createFileFeatures();
    }


    void createFileFeatures() {
        imageFeatures = new ImageFeatures(mainActivity, executorService);
    }



}
