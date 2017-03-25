package com.helpinghands;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by himangi on 25/03/2017.
 */

public class SharedPreferenceHelper {

    private final static String PREF_FILE = "PREF";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String USER_FULL_NAME = "fullName";
    private static final String USER_NAME = "Name";
    private static final String USER_PROFILE = "profile";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String IS_LOGIN = "isLogin";


    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        editor = sharedPreferences.edit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void setIsUserLogin(boolean isLogin) {
        editor.putBoolean(IS_LOGIN,isLogin);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME,userName);
        editor.commit();
    }

    public String getUserPhoneNumber() {
        return sharedPreferences.getString(PHONE_NUMBER, "");
    }

    public void setUserPhoneNumber(String userName) {
        editor.putString(PHONE_NUMBER,userName);
        editor.commit();
    }

    public String getUserFullName() {
        return sharedPreferences.getString(USER_FULL_NAME, "");
    }

    public void setUserFullName(String userFullName) {
        editor.putString(USER_FULL_NAME,userFullName);
        editor.commit();
    }

    public String getUserProfile() {
        return sharedPreferences.getString(USER_PROFILE, "");
    }

    public void setUserProfile(String userProfile) {
        editor.putString(USER_PROFILE,userProfile);
        editor.commit();
    }

}
