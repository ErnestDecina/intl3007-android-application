package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;


public class CreateEntryActivity extends AppCompatActivity {
    ExecutorService executorService;
    SystemFeatures systemFeatures;


    Intent i;
    ActivityResultLauncher<Intent> launcher;


    ArrayList<Uri> uriArrayList = new ArrayList<>();

    // UI Components
    ImageButton buttonGetImages;
    Button buttonSave;
    ImageButton buttonGetAudio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diary);

        createDependencies();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void createDependencies() {
        createThreadExecutor();
        createSystemFeatures();
        setupGetImages();
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

    private void setupButtons() {
        buttonGetImages = findViewById(R.id.buttonGetImages);
        buttonSave = findViewById(R.id.buttonSave);
        buttonGetAudio = findViewById(R.id.buttonGetAudio);

        buttonGetImages.setOnClickListener(v -> getImages());
        buttonSave.setOnClickListener(v -> saveEntry());

    }

    private void setupGetImages() {
        i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        launcher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        (ActivityResult result) -> {
                            if(result.getResultCode() == RESULT_OK) {
                                ArrayList<Uri> imagesArrayList = getURIFromData(result.getData());
                                uriArrayList.addAll(imagesArrayList);
                            } else if(result.getResultCode()==ImagePicker.RESULT_ERROR){
                                // Use ImagePicker.Companion.getError(result.getData()) to show an error
                            }
                        }
                );
    }

    private ArrayList<Uri> getURIFromData(Intent data) {
        ArrayList<Uri> imagesEncodedList = new ArrayList<>();

        if(data.getData() != null){
            Uri mImageUri = data.getData();
            imagesEncodedList.add(mImageUri);
        }

        else {
            if (data.getClipData() == null) return imagesEncodedList;

            ClipData mClipData = data.getClipData();

            for (int i = 0; i < mClipData.getItemCount(); i++) {
                ClipData.Item item = mClipData.getItemAt(i);
                Uri uri = item.getUri();
                imagesEncodedList.add(uri);
            } // End for
        } // End else

        return imagesEncodedList;
    } // End getURIFromData()

    private void getImages() {
        launcher.launch(i);
    }

    private void saveEntry() {
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String dateString = hour + ":" + minutes + " " + day + "/" + month + "/" + year;

        systemFeatures.diaryFeatures.createDiaryEntry(
                "test title",
                "test content",
                    dateString,
                "test_image_url",
                "test_voice_url",
                "test_location",
                "",
                uriArrayList
        );
    }
}