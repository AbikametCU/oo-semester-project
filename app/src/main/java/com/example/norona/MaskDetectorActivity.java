package com.example.norona;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.example.norona.env.Logger;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.tflite.Classifier;

import java.util.List;

public class MaskDetectorActivity extends ClassifierActivity{
    private static final Classifier.Model model = Classifier.Model.FLOAT_MOBILENET_MASK_DETECTOR;
    protected Classifier.Device device = Classifier.Device.CPU;
    protected int numThreads = -1;
    private static final Logger LOGGER = new Logger();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        LOGGER.d("onCreate " + this);
        super.onCreate(null);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.mask_detector_activity_camera);

        if (hasPermission()) {
            setFragment();
        } else {
            requestPermission();
        }

        bottomSheetLayout = findViewById(R.id.mask_detector_bottom_sheet_layout);
        gestureLayout = findViewById(R.id.mask_detector_gesture_layout);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetArrowImageView = findViewById(R.id.mask_detector_bottom_sheet_arrow);

        ViewTreeObserver vto = gestureLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            gestureLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            gestureLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        //                int width = bottomSheetLayout.getMeasuredWidth();
                        int height = gestureLayout.getMeasuredHeight();

                        sheetBehavior.setPeekHeight(height);
                    }
                });
        sheetBehavior.setHideable(false);

        sheetBehavior.setBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_HIDDEN:
                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                            {
                                bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_down);
                            }
                            break;
                            case BottomSheetBehavior.STATE_COLLAPSED:
                            {
                                bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_up);
                            }
                            break;
                            case BottomSheetBehavior.STATE_DRAGGING:
                                break;
                            case BottomSheetBehavior.STATE_SETTLING:
                                bottomSheetArrowImageView.setImageResource(R.drawable.icn_chevron_up);
                                break;
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
                });

        recognitionTextView = findViewById(R.id.detected_item);
        recognitionValueTextView = findViewById(R.id.detected_item_value);
//        recognition1TextView = findViewById(R.id.detected_item1);
//        recognition1ValueTextView = findViewById(R.id.detected_item1_value);
//        recognition2TextView = findViewById(R.id.detected_item2);
//        recognition2ValueTextView = findViewById(R.id.detected_item2_value);
//
//        frameValueTextView = findViewById(R.id.frame_info);
//        cropValueTextView = findViewById(R.id.crop_info);
//        cameraResolutionTextView = findViewById(R.id.view_info);
//        rotationTextView = findViewById(R.id.rotation_info);
//        inferenceTimeTextView = findViewById(R.id.inference_info);

//        modelSpinner.setOnItemSelectedListener(this);
//        deviceSpinner.setOnItemSelectedListener(this);
//
//        plusImageView.setOnClickListener(this);
//        minusImageView.setOnClickListener(this);

//        model = Classifier.Model.valueOf("FLOAT_MOBILENET_MASK_DETECTOR");
//        device = Classifier.Device.valueOf(deviceSpinner.getSelectedItem().toString());
//        numThreads = Integer.parseInt(threadsTextView.getText().toString().trim());
    }

    @Override
    public String chooseCamera() {
        final CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for (final String cameraId : manager.getCameraIdList()) {
                final CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

                // We don't want to use the external facing camera
                final Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                LOGGER.i("C ID: %s", cameraId);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
                    continue;
                }

                final StreamConfigurationMap map =
                        characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                if (map == null) {
                    continue;
                }

                // Fallback to camera1 API for internal cameras that don't have full support.
                // This should help with legacy situations where using the camera2 API causes
                // distorted or otherwise broken previews.
                useCamera2API =
                        (facing == CameraCharacteristics.LENS_FACING_FRONT)
                                || isHardwareLevelSupported(
                                characteristics, CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL);
                LOGGER.i("Camera API lv2?: %s", useCamera2API);
                LOGGER.i("Camera ID: %s", cameraId);
                return cameraId;
            }
        } catch (CameraAccessException e) {
            LOGGER.e(e, "Not allowed to access camera");
        }

        return null;
    }

    @UiThread
    @Override
    protected void showResultsInBottomSheet(List<Classifier.Recognition> results) {
        if (results != null && results.size() >= 1) {
            Classifier.Recognition recognition = results.get(0);
            if (recognition != null) {
                if (recognition.getTitle() != null) recognitionTextView.setText(recognition.getTitle());
                if (recognition.getConfidence() != null)
                    recognitionValueTextView.setText(
                            String.format("%.2f", (100 * recognition.getConfidence())) + "%");
            }
        }
    }


    protected Classifier.Model getModel() {
        return model;
    }
}
