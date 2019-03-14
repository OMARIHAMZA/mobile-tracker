package omari.hamza.mobiletracker.controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import omari.hamza.mobiletracker.core.api.UserServices;
import omari.hamza.mobiletracker.core.models.MyResponse;
import omari.hamza.mobiletracker.core.utils.RetrofitClientInstance;
import omari.hamza.mobiletracker.core.utils.UserUtils;
import retrofit2.Call;
import retrofit2.Callback;

public class UserController {

    public static void loginUser(String mobile, String password, Callback<MyResponse> callback) {
        UserServices userServices = RetrofitClientInstance.getRetrofitInstance().create(UserServices.class);
        Call<MyResponse> call = userServices.loginUser(mobile, password);
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

}
