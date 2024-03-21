package com.ernestjohndecina.intl3007_diary_application.layers.system_features.features;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.ernestjohndecina.intl3007_diary_application.layers.security_layer.SecurityLayer;

import java.io.IOException;
import java.net.URI;
import java.nio.InvalidMarkException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;

public class DiaryFeatures {
    Activity mainActivity;
    ExecutorService executorService;

    SecurityLayer securityLayer;

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
            String title,
            String content,
            String timestamp,
            String image_url,
            String Voice_Rec_url,
            String location,
            String last_update,
            ArrayList<Uri> uriArrayList
    ) {
        // Bitmap ArrayList
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();

        // Convert Uri to Bitmaps
        for (Uri uri: uriArrayList) {
            try { bitmapArrayList.add(MediaStore.Images.Media.getBitmap(mainActivity.getContentResolver(), uri)); }
            catch (IOException e) { throw new RuntimeException(e); }
        } // End foreach




        // Encrypt
        securityLayer.encryptDiaryEntry(
                title,
                content,
                timestamp,
                image_url,
                Voice_Rec_url,
                location,
                last_update,
                bitmapArrayList
        );
    }

}
