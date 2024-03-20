package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;

public class CreateEntryActivity extends AppCompatActivity {
    ExecutorService executorService;
    SystemFeatures systemFeatures;


    Intent i;
    ActivityResultLauncher<Intent> launcher;


    ArrayList<Uri> images = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        createDependencies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getImages();

        Log.d("test", String.valueOf(images.size()));

    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
        setupGetImages();
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

    private void setupGetImages() {
        i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        launcher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        (ActivityResult result) -> {
                            if(result.getResultCode() == RESULT_OK) {
                                Uri uri = result.getData().getData();
                                images.add(uri);
                                // Use the uri to load the image
                            } else if(result.getResultCode()==ImagePicker.RESULT_ERROR){
                                // Use ImagePicker.Companion.getError(result.getData()) to show an error
                            }
                        }
                );
    }

    private void getImages() {
        launcher.launch(i);
    }
}