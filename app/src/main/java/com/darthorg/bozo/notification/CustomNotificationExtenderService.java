package com.darthorg.bozo.notification;

import android.content.SharedPreferences;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONObject;

import static com.darthorg.bozo.view.Definicoes.PREF_CONFIG;

/**
 * Created by gusta on 05/03/2017.
 */

public class CustomNotificationExtenderService extends NotificationExtenderService {

    private SharedPreferences.Editor editor;
    private SharedPreferences preferencias;

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {

        JSONObject data = notification.payload.additionalData;

        if (data != null) {
            try {
                int codigo = data.getInt("version");
                int nivelAtualizacao = data.getInt("nivel_atualizacao");

                if (codigo != 0 && nivelAtualizacao != 0) {
                    preferencias = getSharedPreferences(PREF_CONFIG, MODE_PRIVATE);
                    editor = preferencias.edit();
                    editor.putInt("version", codigo);
                    editor.putInt("nivel_atualizacao", nivelAtualizacao);
                    editor.commit();

                    return true;
                }
            } catch (Exception e) {}
        }
        return false;
    }


}
