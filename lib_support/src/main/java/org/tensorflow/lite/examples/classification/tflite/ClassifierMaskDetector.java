package org.tensorflow.lite.examples.classification.tflite;

import android.app.Activity;

import org.tensorflow.lite.examples.classification.tflite.ClassifierFloatMobileNet;

import java.io.IOException;

public class ClassifierMaskDetector extends ClassifierFloatMobileNet{

    private static final int MAX_RESULTS = 1;

    public ClassifierMaskDetector(Activity activity, Device device, int numThreads)
            throws IOException {
        super(activity, device, numThreads);
    }

    @Override
    protected String getModelPath() {
        return "mask_detector.tflite";
    }

    @Override
    protected String getLabelPath() {
        return "mask_detector_labels.txt";
    }
}
