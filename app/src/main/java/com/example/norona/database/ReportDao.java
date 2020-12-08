package com.example.norona.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Inspiration for implementation of the Room database borrowed from Android Developer codelab
// https://developer.android.com/codelabs/android-room-with-a-view#7
@Dao
public interface ReportDao {
    @Query("SELECT * FROM report")
    LiveData<List<Report>> getAll();

    @Query("SELECT * FROM report WHERE state LIKE :mState")
    LiveData<List<Report>> loadAllByStates(String mState);

    @Query("SELECT * FROM report WHERE state LIKE :mState AND " +
            "datetime(date) >= datetime(:dateStart) AND " +
            "datetime(date) < datetime(:dateEnd)")
    LiveData<List<Report>> loadAllByStatesAndDates(String mState, String dateStart, String dateEnd);
}
