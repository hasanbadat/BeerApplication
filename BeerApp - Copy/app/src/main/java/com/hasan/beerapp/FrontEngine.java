package com.hasan.beerapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.hasan.beerapp.retrofit.RetrofitFactory;

import java.util.HashMap;


/**
 * Created by Hasan on 5/25/2017.
 */
public class FrontEngine extends
        Application {
    private static FrontEngine frontEngine;
    public HashMap<String, String> mapHeader = null;
    private RetrofitFactory retrofitFactory;

    public static FrontEngine getInstance() {
        if (frontEngine == null)
            frontEngine = new FrontEngine();
        return frontEngine;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
//        if (!(e instanceof NullPointerException))
        e.printStackTrace(); // not all Android versions will print the stack trace automatically

        Intent intent = new Intent(this, Splash.class);
        intent.putExtra("error", e.getLocalizedMessage() + "");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        System.exit(2); // kill off the crashed app
    }

    public RetrofitFactory getRetrofitFactory() {
        if (retrofitFactory == null)
            retrofitFactory = new RetrofitFactory();
        return retrofitFactory;
    }
}
