package edu.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        FirebaseApp.initializeApp(this);
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
