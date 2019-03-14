package omari.hamza.mobiletracker.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import omari.hamza.mobiletracker.core.models.User;

public class UserUtils {

    private static final String SHARED_PREFERENCES_TAG = "MY_SHARED_PREFERENCES";
    private static final String USER_TOKEN_TAG = "USER_TOKEN";
    private static final String USERNAME_TAG = "USERNAME_TAG";
    private static final String PHONE_NUMBER_TAG = "PHONE_TAG";

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

    public static User getLoggedUser(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        return new User(sharedPreferences.getString(USERNAME_TAG, ""), sharedPreferences.getString(PHONE_NUMBER_TAG, ""));
    }

    public static boolean isUserLoggedIn(@NonNull Context context){
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_TAG, 0);
        return !mSharedPreferences.getString(USERNAME_TAG, "null").equals("null");
    }

}
