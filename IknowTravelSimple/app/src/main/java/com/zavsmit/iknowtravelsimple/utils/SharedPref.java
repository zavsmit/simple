package com.zavsmit.iknowtravelsimple.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sega on 15.02.2015.
 */
public class SharedPref {

    public static void setIsDataDownloads(boolean isDataDownloads, Context context) {
        SharedPreferences sPref = context.getSharedPreferences("isDataDownloads", 0);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("isDataDownloads", isDataDownloads);
        ed.apply();
    }

    public static boolean getIsDataDownloads(Context context) {
        SharedPreferences sPref = context.getSharedPreferences("isDataDownloads", 0);
        return sPref.getBoolean("isDataDownloads", false);
    }
}
