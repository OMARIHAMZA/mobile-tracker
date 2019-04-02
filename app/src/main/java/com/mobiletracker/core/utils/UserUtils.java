package com.mobiletracker.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.mobiletracker.core.models.User;

public class UserUtils {

    private static final String SHARED_PREFERENCES_TAG = "MY_SHARED_PREFERENCES";
    private static final String USER_TOKEN_TAG = "USER_TOKEN";
    private static final String USERNAME_TAG = "USERNAME_TAG";
    private static final String PHONE_NUMBER_TAG = "PHONE_TAG";
    private static final String LONGITUDE_TAG = "LONGITUDE_TAG";
    private static final String LATITUDE_TAG = "LATITUDE_TAG";
    private static final String LAST_UPDATED_TAG = "LAST_UPDATED_TAG";
    private static final String CONTACTS_TAG = "CONTACTS_TAG";
    private static final String LANGUAGE_TAG = "LANGUAGE_TAG";

    public static void saveToken(@NonNull Context context, String token) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(USER_TOKEN_TAG, token);
        mEditor.apply();
    }

    public static String getToken(@NonNull Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        return mSharedPreferences.getString(USER_TOKEN_TAG, "null");
    }

    public static void saveUserInfo(@NonNull Context context, User user) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(USERNAME_TAG, user.getName());
        mEditor.putString(PHONE_NUMBER_TAG, user.getMobile());
        mEditor.apply();
    }

    public static User getLoggedUser(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        return new User(sharedPreferences.getString(USERNAME_TAG, ""), sharedPreferences.getString(PHONE_NUMBER_TAG, ""));
    }

    public static boolean isUserLoggedIn(@NonNull Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        return !mSharedPreferences.getString(USERNAME_TAG, "null").equals("null");
    }

    public static void resetSharedPreferences(@NonNull Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();
    }

    public static void saveLocation(@NonNull Context context, String latitude, String longitude) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(LATITUDE_TAG, latitude);
        mEditor.putString(LONGITUDE_TAG, longitude);
        mEditor.putString(LAST_UPDATED_TAG, format.format(Calendar.getInstance().getTime()));
        mEditor.apply();
    }

    public static String getLastLocation(@NonNull Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        return "Lat: " + mSharedPreferences.getString(LATITUDE_TAG, "NaN") + "\nLong: " + mSharedPreferences.getString(LONGITUDE_TAG, "NaN") + "\nLast Updated: " + mSharedPreferences.getString(LAST_UPDATED_TAG, " NaN");
    }

    public static void saveArrayList(Context context, ArrayList<String> list) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(CONTACTS_TAG, json);
        editor.apply();
    }

    public static ArrayList<String> getArrayList(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        Gson gson = new Gson();
        String json = prefs.getString(CONTACTS_TAG, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String getDeviceLanguage(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        return sharedPreferences.getString(LANGUAGE_TAG, "en");
    }

    public static void saveDeviceLanguage(@NonNull Context context, String lang){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putString(LANGUAGE_TAG, lang);
        mEditor.apply();
    }
}
