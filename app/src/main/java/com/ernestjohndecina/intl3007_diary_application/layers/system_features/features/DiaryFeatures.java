package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.fragments.SearchDiaryFragment;
import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

import java.io.IOException;
import java.net.URI;
import java.nio.InvalidMarkException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class DiaryFeatures {
    Activity mainActivity;
    ExecutorService executorService;

    SecurityLayer securityLayer;

    List<DiaryEntry> diaryEntries;

    public DiaryFeatures(
        Activity mainActivity,
        ExecutorService executorService,
        SecurityLayer securityLayer
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
        this.securityLayer = securityLayer;
    }

    public void createDiaryEntry(
            Integer userId,
            String title,
            String content,
            String timestamp,
            String location,
            String last_update,
            Integer mood,
            ArrayList<Uri> uriArrayList
    ) {
        executorService.submit(() -> {
            // Bitmap ArrayList
            ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();

            // Convert Uri to Bitmaps
            for (Uri uri: uriArrayList) {
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(mainActivity.getContentResolver(), uri);

                    bitmapArrayList.add(image);
                }
                catch (IOException e) { throw new RuntimeException(e); }
            } // End foreach

            // Encrypt
            securityLayer.encryptDiaryEntry(
                    userId,
                    title,
                    content,
                    timestamp,
                    location,
                    last_update,
                    mood,
                    bitmapArrayList
            );
        });
    }

    public List<DiaryEntry> getAllDiaryEntries() {
        try {
            diaryEntries = securityLayer.decryptAllDiaryEntry();
            return diaryEntries;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public DiaryEntry getDiaryEntry(Long entryId) {
        try {
            diaryEntries = securityLayer.decryptAllDiaryEntry();

            for(DiaryEntry diaryEntry: diaryEntries) {
                if(Objects.equals(diaryEntry.entryID, entryId)) {
                    return diaryEntry;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public ArrayList<Bitmap> getDiaryEntryImages(DiaryEntry entry) {
        return securityLayer.decryptImages(entry);
    }

    public byte[] getDiaryEntryAudio(DiaryEntry entry) {
        return securityLayer.decryptAudio(entry);
    }

    public ArrayList<DiaryEntry> searchDiaryEntry(String searchQuery) {
        // Check if its in date format

        if(searchQuery.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return searchDiaryEntryByDate(searchQuery);
        } else if (searchQuery.matches("Happy")) {
            return searchDiaryEntryByMood(1);
        } else if (searchQuery.matches("Ok")) {
            return searchDiaryEntryByMood(0);
        } else if (searchQuery.matches("Sad")) {
            return searchDiaryEntryByMood(-1);
        }


        // Check for Titles

        return searchDiaryEntryByTitle(searchQuery);
    }


    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat diaryDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private ArrayList<DiaryEntry> searchDiaryEntryByDate(String searchDateString) {
        ArrayList<DiaryEntry> results = new ArrayList<>();
        try {
            Date searchDate = inputDateFormat.parse(searchDateString);

            for (DiaryEntry diaryEntry: diaryEntries) {
                String[] diaryStringDate = diaryEntry.LastUpdate.split(" ");
                Date diaryDate = diaryDateFormat.parse(diaryStringDate[1]);

                if(diaryDate.equals(searchDate)) {
                    results.add(diaryEntry);
                }
            }



        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        return results;
    }

    private  ArrayList<DiaryEntry> searchDiaryEntryByTitle(String title) {
        ArrayList<DiaryEntry> results = new ArrayList<>();

        for (DiaryEntry diaryEntry: diaryEntries) {
            if(diaryEntry.title.contains(title)) {
                results.add(diaryEntry);
            }
        }

        return results;
    }

    private  ArrayList<DiaryEntry> searchDiaryEntryByMood(Integer mood) {
        ArrayList<DiaryEntry> results = new ArrayList<>();

        for (DiaryEntry diaryEntry: diaryEntries) {
            if(diaryEntry.mood == mood) {
                results.add(diaryEntry);
            }
        }

        return results;
    }



}
