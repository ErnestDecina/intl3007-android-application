package com.ernestjohndecina.intl3007_diary_application.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.activities.ViewEditDiaryActivity;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.ViewHolder> {
    private Activity activity;
    private List<DiaryEntry> entries;

    public DiaryEntryAdapter(
            Activity activity,
            List<DiaryEntry> entries
    ) {
        this.activity = activity;
        this.entries = entries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_item_adp, parent, false);
        ViewHolder holder = new ViewHolder(view);

        //
        view.setOnClickListener(v -> openViewEditDiaryActivity(holder.id));


        return holder;
    }

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat displayDateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy HH:mm");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryEntry entry = entries.get(position);
        holder.titleTextView.setText(entry.title);

        try {
            Date date = inputDateFormat.parse(entry.timestamp);
            String dateString = displayDateFormat.format(date);
            holder.date.setText(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



        holder.lastUpdatedTextView.setText("last updated on " + entry.LastUpdate);
        holder.id = entry.entryID;

        if(entry.mood == 1) {
            holder.moodTextView.setText(R.string.mood_happy);
        } else if (entry.mood == 0) {
            holder.moodTextView.setText(R.string.mood_ok);
        } else if (entry.mood == -1) {
            holder.moodTextView.setText(R.string.mood_sad);
        }


    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView date;
        TextView lastUpdatedTextView;
        TextView moodTextView;
        Long id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvTrip);
            date = itemView.findViewById(R.id.tvDate);
            lastUpdatedTextView = itemView.findViewById(R.id.tvLastUpdated);
            moodTextView = itemView.findViewById(R.id.ivIcon);
        }
    }


    public void updateDiaryEntries(List diaryEntries) {
        this.entries = diaryEntries;
    }



    ViewEditDiaryActivity viewEditDiaryActivity = new ViewEditDiaryActivity();

    /*
    *
    *
    *
    */
    public static String ENTRY_ID = "ENTRY_ID";
    private void openViewEditDiaryActivity(Long entryID) {
        Intent intent = new Intent(activity, ViewEditDiaryActivity.class);
        Log.d("TEST", "" + entryID);
        intent.putExtra(ENTRY_ID, entryID);

        this.activity.startActivity(intent);
    }


}
