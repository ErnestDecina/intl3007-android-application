package com.ernestjohndecina.intl3007_diary_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;
import com.ernestjohndecina.intl3007_diary_application.utilites.security.Security;

import java.util.concurrent.ExecutionException;
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
    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
        test();
    }


    private void test() {


        //systemFeatures.userFeatures.createUserAccount("Test LOL 3", "Test LOL 3");
        User user = systemFeatures.userFeatures.getUserAccountDetails();
        Log.d("TEST", "TEST: " + user.username);
         
        // Security security = new Security();
        // security.encryptString("Hello World");
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