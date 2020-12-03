package com.example.norona.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.norona.MaskDetectorActivity;
import org.tensorflow.lite.examples.classification.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Inspiration for this code segment from the following resources:
            // https://stackoverflow.com/questions/15478105/start-an-activity-from-a-fragment
            // https://developer.android.com/guide/topics/ui/controls/button
        Button button = (Button) root.findViewById(R.id.button_classify);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MaskDetectorActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}