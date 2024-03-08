package com.ernestjohndecina.intl3007_diary_application.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @ColumnInfo(name = "uid")
    public Integer userID;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "pin")
    public String pin;
}
