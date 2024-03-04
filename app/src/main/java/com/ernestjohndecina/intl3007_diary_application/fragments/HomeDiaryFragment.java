package com.ernestjohndecina.intl3007_diary_application.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

public class HomeDiaryFragment extends Fragment {
    //
    SystemFeatures systemFeatures;


    public HomeDiaryFragment() {}


    public static HomeDiaryFragment newInstance(

    ) {
        HomeDiaryFragment fragment = new HomeDiaryFragment();
        return fragment;
    }


    @Override
    public void onCreate(
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_diary, container, false);
    }
}