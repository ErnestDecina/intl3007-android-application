package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.fragments.DiaryEntryAdapter;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ViewEditDiaryActivity extends AppCompatActivity {
    ExecutorService executorService;
    SystemFeatures systemFeatures;


    DiaryEntry diaryEntry;
    long entryID;



    private MediaPlayer mediaPlayer;
    private static String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_diary);


        createDependencies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playAudio();


    }

    @Override
    protected  void onStop() {
        super.onStop();

        // Delete cache values
    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
        setupMediaPlayer();
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
        diaryEntry = systemFeatures.diaryFeatures.getAllDiaryEntries().get((int) entryID);

        // Load Images


        // Load Audio
        loadAudio();

        Toast.makeText(this, "Title: " + diaryEntry.title + " Mood: " + diaryEntry.mood, Toast.LENGTH_SHORT).show();
    }

    private void setupMediaPlayer() {
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/view_audio.3gp";
        mediaPlayer = new MediaPlayer();
    }

    private void loadAudio() {
        byte[] audioBytes = systemFeatures.diaryFeatures.getDiaryEntryAudio(diaryEntry);

        if(audioBytes == null) {
            Toast.makeText(this, "No audio", Toast.LENGTH_SHORT).show();
            return;
        }

        // Write audio to cache
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(audioBytes);
            fos.close();
        }
        catch(FileNotFoundException ex)   {
            System.out.println("FileNotFoundException : " + ex);
        }
        catch(IOException ioe)  {
            System.out.println("IOException : " + ioe);
        }
    }

    private void playAudio() {
        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("TEST", "prepare() failed");
        }
    }




}