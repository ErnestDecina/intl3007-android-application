package com.ernestjohndecina.intl3007_diary_application;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;
import com.ernestjohndecina.intl3007_diary_application.utilites.security.Crypt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ExecutorService executorService;

    SystemFeatures systemFeatures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createDependencies();
        test();
    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
    }


    private void test() {

    }

    private void createThreadExecutor() {
        executorService = new ThreadPoolExecutor(
                4,
                10,
                5L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>()
        );
    }

    private void createSystemFeatures() {
        this.systemFeatures = new SystemFeatures(
                this,
                this.executorService
        );

    }
}