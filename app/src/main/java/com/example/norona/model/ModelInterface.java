package com.example.norona.model;

import androidx.lifecycle.LiveData;

import com.example.norona.database.Report;

import java.util.List;
import java.util.Map;

public interface ModelInterface {
    // Initialization
    void initialize();

    // Refreshes CSV with latest version from internet
    void refresh();

    // Queries CSV for array of cases/deaths
    LiveData<List<Report>> query(Map<String, String> terms);
}
