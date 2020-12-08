package com.example.norona.model;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.norona.App;
import com.example.norona.database.AppDatabase;
import com.example.norona.database.Report;

import java.util.List;
import java.util.Map;

public class StatsModel implements ModelInterface {

    private AppDatabase database;

    public StatsModel(final Context context){
        database = AppDatabase.getDatabase(context);
    }

    public void initialize(){
        database = AppDatabase.getDatabase(App.getContext());
    }
    public void refresh(){

    }

    // Queries the database
    public LiveData<List<Report>> query(Map<String, String> terms){
        int numTerms = terms.size();

        Log.i("Number Terms",Integer.toString(numTerms));
        switch ( numTerms ){
            case 1 : {
                LiveData<List<Report>> results =  database.ReportDao().getAll();
                return results;
            }

            case 2 : {
                LiveData<List<Report>> results =  database.ReportDao().loadAllByStates(terms.get("state"));
                return results;
            }

            default : {
                LiveData<List<Report>> results =  database.ReportDao().loadAllByStatesAndDates(terms.get("state"),
                                                                                     terms.get("start"),
                                                                                     terms.get("end"));
                return results;
            }
        }
    }
}
