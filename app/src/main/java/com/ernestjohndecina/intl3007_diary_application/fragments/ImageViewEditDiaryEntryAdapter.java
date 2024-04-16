package com.ernestjohndecina.intl3007_diary_application.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ernestjohndecina.intl3007_diary_application.R;
import com.ernestjohndecina.intl3007_diary_application.activities.ViewEditDiaryActivity;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;

import java.util.List;

public class ImageViewEditDiaryEntryAdapter extends RecyclerView.Adapter<ImageViewEditDiaryEntryAdapter.ViewHolder> {
    private Activity activity;
    private List<Bitmap> entries;

    public ImageViewEditDiaryEntryAdapter(
            Activity activity,
            List<Bitmap> entries
    ) {
        this.activity = activity;
        this.entries = entries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image, parent, false);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageBitmap(entries.get(position));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView5);
        }
    }
}
