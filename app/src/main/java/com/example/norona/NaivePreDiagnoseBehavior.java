package com.example.norona;

public class NaivePreDiagnoseBehavior implements PreDiagnoseBehavior{

    @Override
    public boolean hasCOVID(QuestionnaireReport questionnaireReport){
        float symptomCount = 0;

        for(Symptom symptom: questionnaireReport.getSymptomArrayList()){
            if(symptom.present){
              symptomCount++;
            }
        }

        int numTotalSymptoms = questionnaireReport.getSymptomArrayList().size();
        float symptom_percentage = (symptomCount/numTotalSymptoms)*100;

        if(symptom_percentage > 50){
            return true;
        }

        return false;
    }

}
