package omari.hamza.mobiletracker.core.api;

import omari.hamza.mobiletracker.core.models.MyResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserServices {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<MyResponse> loginUser(@Field("phone") String phone, @Field("password") String password);


    @Headers("Accept: application/json")
    @POST("api/auth/me")
    Call<MyResponse> getUserInfo(@Header("Authorization") String token);

    @GET("api/profile/getFriends")
    Call<MyResponse> getContacts(@Header("Authorization") String token);
}
