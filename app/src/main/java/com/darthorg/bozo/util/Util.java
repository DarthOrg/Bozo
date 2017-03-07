package com.darthorg.bozo.util;

import android.content.Context;
import android.content.pm.PackageInfo;

/**
 * Created by gusta on 05/03/2017.
 */

public class Util {

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            String packageName = context.getPackageName();
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {}

        return packageInfo;
    }



}
