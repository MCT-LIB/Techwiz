package com.csupporter.techwiz;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App getApp() {
        return sInstance;
    }

    public static Context getContext() {
        return getApp().getApplicationContext();
    }
}
