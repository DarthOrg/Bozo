package com.darthorg.bozo.notification;

import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONObject;

/**
 * Created by gusta on 05/03/2017.
 */

public class CustomNotificationExtenderService extends NotificationExtenderService {
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {

        JSONObject data = notification.payload.additionalData;
        try {
            int codigo = data.getInt("version");
            if (codigo != 0) {

                Log.i("log", "DEU BOM : " + codigo);

            } else {
                Log.i("log", "DEU BOM : " + data);
            }
        } catch (Exception e) {

        }
        return false;
    }
}
