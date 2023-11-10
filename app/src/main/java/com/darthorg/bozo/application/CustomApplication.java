package com.darthorg.bozo.application;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

import android.app.Application;

import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

/**
 * Created by gusta on 05/03/2017.
 */

public class CustomApplication extends Application {
    private static final String ONESIGNAL_APP_ID = "0832ba70-d4e7-490f-a5da-30ad9f83fb63";

    @Override
    public void onCreate() {
        super.onCreate();

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);
    }
}
