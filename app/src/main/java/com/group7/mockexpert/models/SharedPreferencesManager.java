package com.group7.mockexpert.models;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static String spKey = "SPKeyHere";
    private static String loginKey = "LoginKeyHere";

    public static void setLogin(String token, Context context){
        SharedPreferences sp = context.getSharedPreferences(spKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(loginKey, token);
        editor.apply();
    }

    public static boolean validateLogin(Context context){
        SharedPreferences sp = context.getSharedPreferences(spKey,Context.MODE_PRIVATE);
        return sp.contains(loginKey);
    }

    public static String getLoginToken(Context context){
        SharedPreferences sp = context.getSharedPreferences(spKey,Context.MODE_PRIVATE);
        return sp.getString(loginKey,"");
    }


}


