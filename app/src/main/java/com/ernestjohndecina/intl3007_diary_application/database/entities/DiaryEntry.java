package com.ernestjohndecina.intl3007_diary_application.database.entities;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entry_ID")
    public Long entryID;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "content")
    public String content;
    @ColumnInfo(name = "timestamp")
    public String timestamp;
    @ColumnInfo(name = "num_images")
    public Integer numImages;
    @ColumnInfo(name = "num_audio")
    public Integer numAudio;
    @ColumnInfo(name = "location")
    public String location;
    @ColumnInfo(name = "last_update")
    public String LastUpdate;

    @ColumnInfo(name = "mood")
    public Integer mood;
}
