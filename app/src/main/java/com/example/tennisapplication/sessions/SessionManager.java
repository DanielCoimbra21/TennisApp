package com.example.tennisapplication.sessions;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("AppKey",0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //Create set login method
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    //Create get login method
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN", false);

    }

    //create set userName method
    public void setUsername(String username){
        editor.putString("KEY_USER", username);
        editor.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString("KEY_USERNAME","");
    }
}
