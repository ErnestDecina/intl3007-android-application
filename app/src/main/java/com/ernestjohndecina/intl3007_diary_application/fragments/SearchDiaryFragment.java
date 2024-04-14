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
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DiaryEntryAdapter(getActivity(), systemFeatures.diaryFeatures.getAllDiaryEntries()); // Populate with dummy entries
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
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                } else if (selectedItem.equals("Oldest")) {
                    linearLayoutManager.setReverseLayout(false);
                    linearLayoutManager.setStackFromEnd(false);
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

        for(int i = 0; i < 10; i++) {
            DiaryEntry temp = new DiaryEntry();
            temp.title = "Title " + i;
            temp.LastUpdate = "test/test/test";

            entries.add(temp);
        }

        return entries;
    }



    public void setSystemFeatures(
            SystemFeatures systemFeatures
    ) {
        this.systemFeatures = systemFeatures;
    }
}