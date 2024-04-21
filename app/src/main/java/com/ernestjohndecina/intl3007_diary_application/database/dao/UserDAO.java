package com.ernestjohndecina.intl3007_diary_application.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ernestjohndecina.intl3007_diary_application.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    List<User> selectUsers();

    @Query("SELECT * FROM user WHERE uid = :userId")
    List<User> selectUser(Integer userId);
}
