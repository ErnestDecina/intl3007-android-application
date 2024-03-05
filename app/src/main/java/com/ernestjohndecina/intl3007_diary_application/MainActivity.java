package com.ernestjohndecina.intl3007_diary_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ernestjohndecina.intl3007_diary_application.activities.CreateDiaryActivity;
import com.ernestjohndecina.intl3007_diary_application.activities.LoginActivity;
import com.ernestjohndecina.intl3007_diary_application.activities.RegisterActivity;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.fragments.HomeDiaryFragment;
import com.ernestjohndecina.intl3007_diary_application.fragments.SearchDiaryFragment;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;
import com.ernestjohndecina.intl3007_diary_application.utilites.security.Crypt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    // Development Variables
    Boolean REGISTER_STATE = true;
    Boolean LOGIN_STATE = true;



    // Dependencies
    ExecutorService executorService;

    SystemFeatures systemFeatures;
    FragmentManager fragmentManager = getSupportFragmentManager();


    // Activities
    Intent registerActivity;
    Intent loginActivity;
    Intent addDiaryEntryActivity;


    // Fragments
    HomeDiaryFragment homeDiaryFragment;
    SearchDiaryFragment searchDiaryFragment;


    // Navigation Buttons
    Button homeButton;
    Button addDiaryButton;
    Button searchDiaryButton;


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
        setupFragments();
        setupActivities();
        setupButtons();
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


    private void setupActivities() {
        registerActivity = new Intent(this, RegisterActivity.class);
        loginActivity = new Intent(this, LoginActivity.class);
        addDiaryEntryActivity = new Intent(this, CreateDiaryActivity.class);
    }


    private void setupFragments() {
        homeDiaryFragment = HomeDiaryFragment.newInstance();
        homeDiaryFragment.setSystemFeatures(systemFeatures);

        searchDiaryFragment = SearchDiaryFragment.newInstance();
        searchDiaryFragment.setSystemFeatures(systemFeatures);
    }


    private void setupButtons() {
        homeButton = findViewById(R.id.HomeButton);
        addDiaryButton = findViewById(R.id.AddDiaryButton);
        searchDiaryButton = findViewById(R.id.SearchDiaryButton);

        homeButton.setOnClickListener( v -> changeFragmentHome() );
        addDiaryButton.setOnClickListener( v -> showAddDiaryEntryActivity() );
        searchDiaryButton.setOnClickListener( v -> changeFragmentSearch() );
    }


    private void test() {
    }


    private Boolean checkUserRegistered() {
        return REGISTER_STATE;
    }


    private Boolean checkUserLoggedIn() {
        return LOGIN_STATE;
    }


    private void showRegisterActivity() {
        if(!checkUserRegistered()) return;
        startActivity(registerActivity);
    }


    private void showLoginActivity() {
        if(!checkUserLoggedIn()) return;
        startActivity(loginActivity);
    }


    private void changeFragmentHome() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, homeDiaryFragment, null)
                .commit();
    }


    private void changeFragmentSearch() {
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, searchDiaryFragment, null)
                .commit();
    }


    private void showAddDiaryEntryActivity() {
        startActivity(addDiaryEntryActivity);
    }

}