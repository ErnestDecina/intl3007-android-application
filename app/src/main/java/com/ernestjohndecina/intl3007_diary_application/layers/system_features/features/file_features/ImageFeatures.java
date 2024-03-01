package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features.file_features;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.ernestjohndecina.intl3007_diary_application.MainActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ImageFeatures {
    MainActivity mainActivity;
    ExecutorService executorService;


    public ImageFeatures(
            MainActivity mainActivity,
            ExecutorService executorService
    ) {
        this.mainActivity = mainActivity;
        this.executorService = executorService;
    }


    public Bitmap selectImageSingle() {
        Uri imageURI = null;


        ActivityResultLauncher<Intent> test = mainActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                });


        ImagePicker.with(mainActivity)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .createIntent(intent -> test.launch(intent))
                .start();

        ImagePicker.with(mainActivity)
                .setImageProviderInterceptor(imageProvider -> null)


        return null;
    }

    public ArrayList<Bitmap> selectImageMultiple() {
        return null;
    }






}
