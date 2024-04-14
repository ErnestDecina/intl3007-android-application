package com.ernestjohndecina.intl3007_diary_application.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat outputDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat displayDateFormat = new SimpleDateFormat("MMMM yyyy HH:mm");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");


    ArrayList<Uri> uriArrayList = new ArrayList<>();

    // UI Components

    // Text Views
    TextView dayTextView;
    TextView monthYearTimeTextView;

    // Image Buttons
    ImageButton buttonCancelDiaryEntry;
    ImageButton buttonGetImages;
    Button buttonSave;
    ImageButton buttonGetAudio;

    // Edit Text
    EditText titleEditText;
    EditText contentEntryEditText;


    Integer mood = 1;


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
        setupMediaRecorderPlayer();
        setupButtons();
        setupEditText();
        setupTextViews();
        setupMoodDropdown();
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

        // Get Date
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int year = Calendar.getInstance().get(Calendar.YEAR);

        Date date = null;

        try {
            date = inputDateFormat.parse(hour + ":" + minutes + " " + day + "/" + month + "/" + year);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String dateString = displayDateFormat.format(date);

        dayTextView.setText(String.valueOf(day));
        monthYearTimeTextView.setText(dateString);

    }

    private void setupEditText() {
        this.titleEditText = findViewById(R.id.titleTextView);
        this.contentEntryEditText = findViewById(R.id.contentEntryTextView);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupButtons() {
        buttonCancelDiaryEntry = findViewById(R.id.cancelImageButton2);
        buttonGetImages = findViewById(R.id.buttonGetImages);
        buttonSave = findViewById(R.id.buttonSave);
        buttonGetAudio = findViewById(R.id.buttonGetAudio);

        buttonCancelDiaryEntry.setOnClickListener(v -> finish());
        buttonGetImages.setOnClickListener(v -> getImages());
        buttonSave.setOnClickListener(v -> saveEntry());
        buttonGetAudio.setOnClickListener(v -> startGetAudio());

        buttonGetAudio.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startGetAudio();

                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                stopGetAudio();

                return true;
            }

            return false;
        });

    }

    private void setupMoodDropdown() {
        Spinner dropdown = findViewById(R.id.moodSpinner);

        ArrayAdapter<CharSequence> dropdownAdapter = ArrayAdapter.createFromResource(this,
                R.array.mood_options, android.R.layout.simple_spinner_item);
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        dropdown.setAdapter(dropdownAdapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = parentView.getItemAtPosition(position).toString();
                // You can perform different actions based on the selected item
                if (selectedItem.equals("\uD83D\uDE0A")) {
                    // Handle when "Newest" is selected
                    mood = 1;
                } else if (selectedItem.equals("\uD83D\uDE10")) {
                    // Handle when "Oldest" is selected
                    mood = 0;

                } else if (selectedItem.equals("\uD83D\uDE14")) {
                    // Handle when "Oldest" is selected
                    mood = -1;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });
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
        // Get Text
        String title = String.valueOf(titleEditText.getText());
        String content = String.valueOf(contentEntryEditText.getText());


        // Get Date
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int year = Calendar.getInstance().get(Calendar.YEAR);

        Date date = null;

        try {
            date = inputDateFormat.parse(hour + ":" + minutes + " " + day + "/" + month + "/" + year);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String dateString = outputDateFormat.format(date);
        Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();
        systemFeatures.diaryFeatures.createDiaryEntry(
                title,
                content,
                dateString,
                "test location",
                dateString,
                mood,
                uriArrayList
        );
    }


    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};


    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private static String fileName = null;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    private void setupMediaRecorderPlayer() {
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/create_audio.3gp";

        this.mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("TEST", "prepare() failed");
        }

        mediaPlayer = new MediaPlayer();


    }

    private void stopGetAudio() {
        buttonGetAudio.setImageResource(R.drawable.cat_mood);
        try {
            mediaRecorder.stop();
        } catch (Exception ignored) {

        }



        try {
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("TEST", "prepare() failed");
        }
    }

    private void startGetAudio() {
        buttonGetAudio.setImageResource(R.drawable.loading);
        setupMediaRecorderPlayer();
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        try {
            mediaRecorder.start();
        } catch (Exception ignored) {

        }

    }
}