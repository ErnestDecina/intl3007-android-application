package com.ernestjohndecina.intl3007_diary_application.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ernestjohndecina.intl3007_diary_application.database.dao.DiaryEntryDAO;
import com.ernestjohndecina.intl3007_diary_application.database.dao.UserDAO;
import com.ernestjohndecina.intl3007_diary_application.database.entities.DiaryEntry;
import com.ernestjohndecina.intl3007_diary_application.database.entities.User;


@Database(
        entities = {User.class, DiaryEntry.class},
        version = 1
)
public abstract class DiaryDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract DiaryEntryDAO diaryEntryDao();
}
