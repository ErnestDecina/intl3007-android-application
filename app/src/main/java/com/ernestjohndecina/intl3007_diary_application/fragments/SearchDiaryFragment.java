package com.ernestjohndecina.intl3007_diary_application.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDiaryFragment extends Fragment {
    SystemFeatures systemFeatures;


    public SearchDiaryFragment() {
        // Required empty public constructor
    }


    public static SearchDiaryFragment newInstance() {
        return new SearchDiaryFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_diary, container, false);
    }


    public void setSystemFeatures(
            SystemFeatures systemFeatures
    ) {
        this.systemFeatures = systemFeatures;
    }
}