package com.ernestjohndecina.intl3007_diary_application.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiaryEntry {
    @PrimaryKey
    @ColumnInfo(name = "entry_ID")
    public Integer entryID;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "content")
    public String content;
    @ColumnInfo(name = "timestamp")
    public String timestamp;
    @ColumnInfo(name = "image_url")
    public String imageUrl;
    @ColumnInfo(name = "voice_rec_url")
    public String VoiceRecUrl;
    @ColumnInfo(name = "location")
    public String location;
    @ColumnInfo(name = "last_update")
    public String LastUpdate;

}
