package com.mobiletracker.controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mobiletracker.core.api.UserServices;
import com.mobiletracker.core.models.ContactsResponse;
import com.mobiletracker.core.models.MyResponse;
import com.mobiletracker.core.utils.RetrofitClientInstance;
import com.mobiletracker.core.utils.UserUtils;
import retrofit2.Call;
import retrofit2.Callback;

public class UserController {

    public static void loginUser(String username, String password, Callback<MyResponse> callback) {
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.loginUser(username, password);
        call.enqueue(callback);
    }

    public static void registerUser(String username, String password, String phone, String phoneBrand, String phoneModel, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.registerUser(username, password, phone, phoneBrand, phoneModel);
        call.enqueue(callback);
    }

    public static void getUserInfo(@NonNull Context context, Callback<MyResponse> callback) {
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.getUserInfo("bearer " + UserUtils.getToken(context));
        call.enqueue(callback);
    }

    public static void getContacts(@NonNull Context context, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.getContacts("bearer " + UserUtils.getToken(context));
        call.enqueue(callback);
    }

    public static void getConnectionRequests(@NonNull Context context, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.getConnectionRequests("bearer " + UserUtils.getToken(context));
        call.enqueue(callback);
    }

    public static void addConnection(@NonNull Context context, String phone, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.connectDevice("bearer " + UserUtils.getToken(context), phone);
        call.enqueue(callback);
    }

    public static void acceptConnectionRequest(@NonNull Context context, String requestId, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.acceptConnectinRequest("bearer " + UserUtils.getToken(context),
                requestId);
        call.enqueue(callback);
    }

    public static void rejectConnectionRequest(@NonNull Context context, String requestId, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.rejectConnectionRequest("bearer " + UserUtils.getToken(context),
                requestId);
        call.enqueue(callback);
    }

    public static void updateLocation(@NonNull Context context, String latitude, String longitude, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.refreshLocation("bearer " + UserUtils.getToken(context), longitude, latitude);
        call.enqueue(callback);
    }

    public static void uploadContacts(@NonNull Context context, String contacts, Callback<MyResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.uploadContacts("bearer " + UserUtils.getToken(context), contacts);
        call.enqueue(callback);
    }

    public static void getAllContacts(@NonNull Context context, Callback<ContactsResponse> callback){
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<ContactsResponse> call = userServices.getAllContacts("bearer " + UserUtils.getToken(context));
        call.enqueue(callback);
    }

}
