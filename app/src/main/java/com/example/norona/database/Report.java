package com.example.norona.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Inspiration for implementation of the Room database borrowed from Android Developer codelab
// https://developer.android.com/codelabs/android-room-with-a-view#7
@Entity
public class Report {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "county")
    public String county;

    @ColumnInfo(name = "state")
    public String state;

    @ColumnInfo(name = "fips")
    public String fips;

    @ColumnInfo(name = "cases")
    public String cases;

    @ColumnInfo(name = "deaths")
    public String deaths;

    public String getCases(){
        return cases;
    }

    public String getDeaths(){
        return deaths;
    }
}