package com.example.norona.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.norona.NaivePreDiagnoseBehavior;
import com.example.norona.PreDiagnoseBehavior;
import com.example.norona.PreDiagnoser;
import com.example.norona.QuestionnaireReport;
import com.example.norona.Symptom;

import org.tensorflow.lite.examples.classification.R;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button questionnaire_submit_button = root.findViewById(R.id.button_questionnaire_submit);
        questionnaire_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    preDiagnose();
            }
        });

        return root;
    }

    public void preDiagnose() {
        ArrayList<CheckBox> checkBoxArrayList = new ArrayList<CheckBox>();

        TextView covidResult = (TextView)getView().findViewById(R.id.covid_result);

        checkBoxArrayList.add((CheckBox)getView().findViewById(R.id.symptom1));
        checkBoxArrayList.add((CheckBox)getView().findViewById(R.id.symptom2));
        checkBoxArrayList.add((CheckBox)getView().findViewById(R.id.symptom3));
        checkBoxArrayList.add((CheckBox)getView().findViewById(R.id.symptom4));
        checkBoxArrayList.add((CheckBox)getView().findViewById(R.id.symptom5));
        checkBoxArrayList.add((CheckBox)getView().findViewById(R.id.symptom6));

        QuestionnaireReport questionnaireReport = new QuestionnaireReport();
        questionnaireReport.setSymptomArrayList(new ArrayList<>());

        PreDiagnoseBehavior preDiagnoseBehavior = new NaivePreDiagnoseBehavior();
        PreDiagnoser preDiagnoser = new PreDiagnoser(preDiagnoseBehavior,questionnaireReport);

        for(CheckBox checkbox: checkBoxArrayList){
                Symptom symptom = new Symptom(checkbox.getText().toString(),checkbox.isChecked());
                questionnaireReport.getSymptomArrayList().add(symptom);
        }

        if(preDiagnoser.preDiagnose()) {
            covidResult.setText("probably +ve");
        }else {
            covidResult.setText("probably -ve");
        }

        covidResult.setVisibility(View.VISIBLE);
    }

}