package com.example.norona.ui.stats;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


// Implementation from this Android App Development Tutorial:
// https://developer.android.com/guide/topics/ui/controls/pickers
public class DatePickerFragment extends DialogFragment {

    // Inspiration for this implementation borrowed from:
    // http://androidtrainningcenter.blogspot.com/2012/10/creating-datepicker-using.html
    DatePickerDialog.OnDateSetListener dateListener;
    private int year,month,day;
    private TextView outputView;

    public DatePickerFragment(DatePickerDialog.OnDateSetListener listener, Bundle savedInstanceState, TextView view){
        dateListener = listener;
        super.setArguments(savedInstanceState);

        year = savedInstanceState.getInt("year");
        month = savedInstanceState.getInt("month");
        day = savedInstanceState.getInt("day");

        outputView = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), dateListener, year, month, day);
    }

//    public void onDateSet(DatePicker view, int year, int month, int day) {
//        // Do something with the date chosen by the user
//        TextView startDate = (TextView) view.findViewById(R.id.start_picker);
//        startDate.setText((month+1)+"/"+day+"/"+year);
//    }
}
