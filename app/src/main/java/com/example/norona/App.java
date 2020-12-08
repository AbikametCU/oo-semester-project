package com.example.norona;

import android.app.Application;
import android.content.Context;

// Inspiration for this class here (https://stackoverflow.com/questions/12320857/how-to-get-my-activity-context)
// In order to access context anywhere in application
public class App extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }
}
