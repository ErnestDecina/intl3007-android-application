package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.fragments.DiaryEntryAdapter;
import com.ernestjohndecina.intl3007_diary_application.fragments.ImageViewEditDiaryEntryAdapter;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ViewEditDiaryActivity extends AppCompatActivity {
    ExecutorService executorService;
    SystemFeatures systemFeatures;


    DiaryEntry diaryEntry;
    ArrayList<Bitmap> imageArrayList = new ArrayList<>();
    long entryID;



    private MediaPlayer mediaPlayer;
    private static String fileName;

    //
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMMM yyyy HH:mm");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");


    // UI Components
    // Text Views
    TextView dayTextView;
    TextView monthYearTimeTextView;
    TextView diaryEntryTitleTextView;
    TextView diaryEntryContentTextView;
    TextView moodTextVIew;

    // Recycler View
    RecyclerView imageRecyclerView;
    ImageButton audioImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_diary);


        createDependencies();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected  void onStop() {
        super.onStop();
        deleteCache();
    }

    private void deleteCache(){
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/view_audio.3gp";

        File audioFile = new File(fileName);
        audioFile.delete();
    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
        setupMediaPlayer();
        getEntryID();
        setupAudioButton();
        loadDiaryEntry();
        setupTextViews();
        setupExitButton();
        setupRecyclerView();
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

    private void setupTextViews() {
        dayTextView = findViewById(R.id.dayTextView2);
        monthYearTimeTextView = findViewById(R.id.monthYearTimeTextView2);
        diaryEntryTitleTextView = findViewById(R.id.titleTextView);
        diaryEntryContentTextView = findViewById(R.id.contentEntryTextView);
        moodTextVIew = findViewById(R.id.emojiDisplayTextView);


        // Get day and date
        try {
            Log.d("TEST", diaryEntry.LastUpdate);
            Date date = inputDateFormat.parse(diaryEntry.LastUpdate);

            String dateString = outputDateFormat.format(date);
            Integer day = date.getDate();

            dayTextView.setText(String.valueOf(day));
            monthYearTimeTextView.setText(dateString);
        } catch (Exception e) {

        }

        diaryEntryTitleTextView.setText(diaryEntry.title);
        diaryEntryContentTextView.setText(diaryEntry.content);

        if(diaryEntry.mood == 1) {
            moodTextVIew.setText(R.string.mood_happy);
        } else if (diaryEntry.mood == 0) {
            moodTextVIew.setText(R.string.mood_ok);
        } else if (diaryEntry.mood == -1) {
            moodTextVIew.setText(R.string.mood_sad);
        }
    }

    private void setupExitButton() {
        ImageButton exitImageButton = findViewById(R.id.cancelImageButton2);
        exitImageButton.setOnClickListener(v -> finish());
    }

    private void setupAudioButton() {
        audioImageButton = findViewById(R.id.audioImageButton);
        audioImageButton.setOnClickListener(v -> playAudio());
    }

    private void setupRecyclerView() {
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        imageRecyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(imageRecyclerView);
        ImageViewEditDiaryEntryAdapter imageViewEditDiaryEntryAdapter = new ImageViewEditDiaryEntryAdapter(this, imageArrayList);
        imageRecyclerView.setAdapter(imageViewEditDiaryEntryAdapter);
    }

    private void getEntryID() {
        Bundle bundle = getIntent().getExtras();
        entryID = bundle.getLong(DiaryEntryAdapter.ENTRY_ID);
    }

    private void loadDiaryEntry() {
        diaryEntry = systemFeatures.diaryFeatures.getDiaryEntry(entryID);

        // Load Images
        loadImages();

        // Load Audio
        loadAudio();
    }

    private void setupMediaPlayer() {
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/view_audio.3gp";
        mediaPlayer = new MediaPlayer();
    }

    private void loadAudio() {
        byte[] audioBytes = systemFeatures.diaryFeatures.getDiaryEntryAudio(diaryEntry);

        if(audioBytes == null) {
            audioImageButton.setVisibility(View.GONE);
            return;
        }

        // Write audio to cache
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(audioBytes);
            fos.close();

            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
        }
        catch(FileNotFoundException ex)   {
            System.out.println("FileNotFoundException : " + ex);
        }
        catch(IOException ioe)  {
            System.out.println("IOException : " + ioe);
        }
    }

    private void loadImages() {
        imageArrayList = systemFeatures.diaryFeatures.getDiaryEntryImages(diaryEntry);
    }

    private void playAudio() {
        mediaPlayer.start();
    }




}