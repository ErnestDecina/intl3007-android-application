package com.ernestjohndecina.intl3007_diary_application.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;

public class HomeDiaryFragment extends Fragment {

    private SystemFeatures systemFeatures;

    public HomeDiaryFragment() {}

    public static HomeDiaryFragment newInstance() {
        return new HomeDiaryFragment();
    }

        // Set up greeting text with the username
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home_diary, container, false);

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
}
