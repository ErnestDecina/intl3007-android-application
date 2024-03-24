package com.ernestjohndecina.intl3007_diary_application.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

////
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.layers.system_features.SystemFeatures;
import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDiaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDiaryFragment extends Fragment {
    SystemFeatures systemFeatures;
    private RecyclerView recyclerView;
    private DiaryEntryAdapter adapter;
    private Spinner dropdown;

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

        View view = inflater.inflate(R.layout.fragment_search_diary, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewDiaryEntries);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DiaryEntryAdapter(getDummyDiaryEntries()); // Populate with dummy entries
        recyclerView.setAdapter(adapter);

        dropdown = view.findViewById(R.id.dropdown);
        ArrayAdapter<CharSequence> dropdownAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.dropdown_options, android.R.layout.simple_spinner_item);
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dropdownAdapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = parentView.getItemAtPosition(position).toString();
                // You can perform different actions based on the selected item
                if (selectedItem.equals("Newest")) {
                    // Handle when "Newest" is selected
                } else if (selectedItem.equals("Oldest")) {
                    // Handle when "Oldest" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        return view;
    }


    private List<DiaryEntry> getDummyDiaryEntries() {
        List<DiaryEntry> entries = new ArrayList<>();
        entries.add(new DiaryEntry("Diary Entry 1", "Content for Diary Entry 1"));
        entries.add(new DiaryEntry("Diary Entry 2", "Content for Diary Entry 2"));
        return entries;
    }



    public void setSystemFeatures(
            SystemFeatures systemFeatures
    ) {
        this.systemFeatures = systemFeatures;
    }
}