package com.darthorg.bozo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gusta on 05/03/2017.
 */

public class Util {

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            String packageName = context.getPackageName();
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
        }

        return packageInfo;
    }

    public static boolean existeConexao(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();

            // Se não existe nenhum tipo de conexão retorna false
            if (netInfo == null) {
                return false;
            }
            int netType = netInfo.getType();

            // Verifica se a conexão é do tipo WiFi ou Mobile e
            // retorna true se estiver conectado ou false em
            // caso contrário
            if (netType == ConnectivityManager.TYPE_WIFI ||
                    netType == ConnectivityManager.TYPE_MOBILE) {
                return netInfo.isConnected();

            } else {
                return false;
            }
        }
        return false;

    }


}
