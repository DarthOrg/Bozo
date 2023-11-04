package com.darthorg.bozo.application;

import android.app.Application;

//import com.onesignal.OneSignal;

/**
 * Created by gusta on 05/03/2017.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        OneSignal.startInit(this).init();
    }
}
