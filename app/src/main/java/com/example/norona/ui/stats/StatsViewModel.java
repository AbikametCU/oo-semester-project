package com.example.norona.ui.stats;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.norona.database.Report;
import com.example.norona.model.ModelInterface;
import com.example.norona.model.StatsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatsViewModel extends ViewModel {

    private LiveData<List<Report>> stats;
    private StatsModel statsModel;

    public StatsViewModel(Context context) {
        stats = new MutableLiveData<>();
        statsModel = new StatsModel(context);
    }

    public ArrayList<Integer> getStats() {
        try{
            return listToInts(stats, "cases");
        }
        catch (NoSuchFieldException ex) { Logger.getLogger(ModelInterface.class.getName()).log(Level.SEVERE, null, ex); }
        finally {
            ArrayList<Integer> placeholder = new ArrayList<Integer>();
            return placeholder;
        }
    }

    // Queries the stats model.
    public void query(Map<String, String> queryElements) {

        stats = statsModel.query(queryElements);

    }

    public ArrayList<Integer> listToInts(LiveData<List<Report>> results, String resultType) throws NoSuchFieldException {
        ArrayList<Integer> convertedResults = new ArrayList<Integer>();
        for (int i = 0; i < results.getValue().size(); i++){
            convertedResults.add(Integer.getInteger(results.getValue().get(i).getClass().getField(resultType).toString()));
        }
        return convertedResults;
    }
}