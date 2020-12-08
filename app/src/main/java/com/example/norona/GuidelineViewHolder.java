package com.example.norona;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.tensorflow.lite.examples.classification.R;

public class GuidelineViewHolder extends RecyclerView.ViewHolder {
    private TextView guidelinesTextView;
    public GuidelineViewHolder(@NonNull View itemView) {
        super(itemView);
        guidelinesTextView = (TextView)itemView.findViewById(R.id.guideline_text);
    }

    public void bindData(final GuidelineTextViewModel viewModel) {
        guidelinesTextView.setText(viewModel.getGuidelineText());
    }

}
