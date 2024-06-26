package com.ernestjohndecina.intl3007_diary_application.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

import java.util.ArrayList;

public class HomeDiaryFragment extends Fragment {

    private SystemFeatures systemFeatures;
    private ArrayList<DiaryEntry> diaryEntryArrayList;

    public HomeDiaryFragment() {}

    public static HomeDiaryFragment newInstance() {
        return new HomeDiaryFragment();
    }
        View view;

        RecyclerView entryRecyclerView;

        // Set up greeting text with the username
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_home_diary, container, false);
            setEntryRecyclerView(view);


            // Set up greeting text with the username
            TextView greetingTextView = view.findViewById(R.id.greetingTextView);

            // Check if systemFeatures is not null and userFeatures is available
            if (systemFeatures != null && systemFeatures.userFeatures != null) {
                // Get the user information
                User userInfo = systemFeatures.userFeatures.getUserAccountDetails();
                String firstName = userInfo.firstName;

                // Display the greeting with the user's first name
                greetingTextView.setText("Good Morning, " + firstName);
            } else {
                // Fallback to a default greeting if user information is not available
                greetingTextView.setText("Good Morning, User");
            }





            return view;
        }


    public void setSystemFeatures(SystemFeatures systemFeatures) {
        this.systemFeatures = systemFeatures;
    }

    public void setEntryRecyclerView(View view) {
            this.entryRecyclerView = view.findViewById(R.id.entryRecyclerView);
            this.entryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            // Get Diary Entries
            diaryEntryArrayList = (ArrayList<DiaryEntry>) systemFeatures.diaryFeatures.getAllDiaryEntries();

            DiaryEntryAdapter adapter = new DiaryEntryAdapter(diaryEntryArrayList); // Populate with dummy entries
            this.entryRecyclerView.setAdapter(adapter);
    }
}
