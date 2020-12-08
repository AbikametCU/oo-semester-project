package com.example.norona.ui.stats;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.tensorflow.lite.examples.classification.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StatsFragment extends Fragment {

    private View optionsView;
    private GraphView graph;
    private TextView startDate;
    private TextView endDate;
    private Spinner stateSpinner;
    private StatsViewModel statsModel;
    private DatePickerDialog.OnDateSetListener start_listener;
    private DatePickerDialog.OnDateSetListener end_listener;
    public static final int START_CODE = 0;
    public static final int END_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        statsModel = new StatsViewModel(getContext());

        graph = (GraphView) root.findViewById(R.id.stats_graph);

        // Inspiration for this code segment from the following resources:
        // https://stackoverflow.com/questions/15478105/start-an-activity-from-a-fragment
        // https://developer.android.com/guide/topics/ui/controls/button
        startDate = (TextView) root.findViewById(R.id.start_picker);

        startDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    showDatePicker(startDate);
            }
        });

        // Inspiration for this code segment from the following resources:
        // https://stackoverflow.com/questions/15478105/start-an-activity-from-a-fragment
        // https://developer.android.com/guide/topics/ui/controls/button
        endDate = (TextView) root.findViewById(R.id.end_picker);

        endDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDatePicker(endDate);
            }
        });

        // Spinner code inspired by Android Developers example:
        // https://developer.android.com/guide/topics/ui/controls/spinner
        stateSpinner = (Spinner) root.findViewById(R.id.state_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        // Inspiration for this code segment from the following resources:
        // https://stackoverflow.com/questions/15478105/start-an-activity-from-a-fragment
        // https://developer.android.com/guide/topics/ui/controls/button
        Button button = (Button) root.findViewById(R.id.refresh_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queryDatabase();
            }
        });

        Map<String, String> queryElements = new HashMap<String, String>() {{
            put("start", "2020-10-10");
            put("end", "2020-10-30");
            put("state", "Colorado");
            put("resultType", "cases");
        }};

        statsModel.query(queryElements);

        return root;
    }

    // Inspiration for this method of showing calendar from following blog:
    // http://androidtrainningcenter.blogspot.com/2012/10/creating-datepicker-using.html
    public void showDatePicker(TextView view){
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker d, int year, int monthOfYear,
                                  int dayOfMonth) {
                view.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth));
            }
        };

        DatePickerFragment picker = new DatePickerFragment(listener, args, view);
        picker.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(), "Date Picker");
    }


    public void queryDatabase() {
        String [] startDateElements = startDate.getText().toString().split("/",3);
        String [] endDateElements = endDate.getText().toString().split("/",3);

        String state = stateSpinner.getSelectedItem().toString();

        Map<String, String> queryElements = new HashMap<String, String>() {{
            put("start", startDateElements[0]+"-"+startDateElements[1]+"-"+startDateElements[2]);
            put("end", endDateElements[0]+"-"+endDateElements[1]+"-"+endDateElements[2]);
            put("state", state);
            put("resultType", "cases");
        }};

        Log.i("start",queryElements.get("start"));
        Log.i("end",queryElements.get("end"));
        Log.i("state",queryElements.get("state"));

        statsModel.query(queryElements);
        ArrayList<Integer> stats = statsModel.getStats();

        updateGraph(stats);
    }

    public void updateGraph(ArrayList<Integer> stats) {

        // This exampled borrowed from the GraphView Documentation:
        // https://github.com/jjoe64/GraphView
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < stats.size(); i++){
            series.appendData(new DataPoint((i),stats.get(i)),true,1000);
        }
        graph.addSeries(series);
    }

}