package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.fragments.DiaryEntryAdapter;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ViewEditDiaryActivity extends AppCompatActivity {
    ExecutorService executorService;
    SystemFeatures systemFeatures;


    long entryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_diary);


        createDependencies();
    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
        getEntryID();
        loadDiaryEntry();
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

    private void getEntryID() {
        Bundle bundle = getIntent().getExtras();
        entryID = bundle.getLong(DiaryEntryAdapter.ENTRY_ID);
    }

    private void loadDiaryEntry() {
        DiaryEntry diaryEntry = systemFeatures.diaryFeatures.getAllDiaryEntries().get((int) entryID);

        Toast.makeText(this, "Title: " + diaryEntry.title + " " + entryID, Toast.LENGTH_SHORT).show();
    }


}