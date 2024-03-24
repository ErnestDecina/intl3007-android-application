package com.ernestjohndecina.intl3007_diary_application.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;

import java.util.List;
@Dao
public interface DiaryEntryDAO {
    @Insert
    Long insertDiaryEntry(DiaryEntry diaryentry);

    @Query("SELECT * FROM DiaryEntry")
    List<DiaryEntry> selectAllDiaryEntry();


}
