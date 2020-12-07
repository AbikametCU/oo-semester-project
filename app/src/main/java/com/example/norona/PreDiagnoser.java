package com.example.norona;

public class PreDiagnoser {
    PreDiagnoseBehavior preDiagnoseBehavior;
    QuestionnaireReport questionnaireReport;

    public PreDiagnoser(PreDiagnoseBehavior preDiagnoseBehavior, QuestionnaireReport questionnaireReport) {
        this.preDiagnoseBehavior = preDiagnoseBehavior;
        this.questionnaireReport = questionnaireReport;
    }

    public boolean preDiagnose() {
        return preDiagnoseBehavior.hasCOVID(this.questionnaireReport);
    }
}
