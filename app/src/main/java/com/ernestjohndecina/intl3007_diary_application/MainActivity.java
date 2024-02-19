package com.ernestjohndecina.intl3007_diary_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

public class MainActivity extends AppCompatActivity {
    SystemFeatures systemFeatures = new SystemFeatures();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        test();
    }


    private void test() {
        systemFeatures.addDiaryEntry("Test Date", "Test Diary Message");
    }
}