package com.ernestjohndecina.intl3007_diary_application;

import androidx.appcompat.app.AppCompatActivity;
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
    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
        test();
    }


    private void test() {
        // Create a user
        systemFeatures.userFeatures.createUserAccount("Test LOL 10", "Test LOL 10");

        // Read User details
        User user = systemFeatures.userFeatures.getUserAccountDetails();

        // Log user Details
        Log.d("TEST", "Decrypted Username: " + user.username);
        Log.d("TEST", "Decrypted Password: " + user.password);

        // Test Login
        Boolean testLoginInTRUE = systemFeatures.userFeatures.validateUser("Test LOL 10", "Test LOL 10");
        Boolean testLoginInFALSE = systemFeatures.userFeatures.validateUser("WRONG USERNAME", "WRONG PASSWORD");

        Log.d("TEST", "Login True: " + testLoginInTRUE);
        Log.d("TEST", "Login False: " + testLoginInFALSE);



//        // Encryption Test
//        Crypt crypt = new Crypt();
//        crypt.init();
//        String encryptedString = crypt.encrypt("Hello World");
//        String decryptedString = crypt.decrypt(encryptedString);
//
//        Log.d("TEST", "Encrypted: " + encryptedString);
//        Log.d("TEST", "Decrypted: " + decryptedString);

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