package omari.hamza.mobiletracker.core.api;

import omari.hamza.mobiletracker.core.models.ContactsResponse;
import omari.hamza.mobiletracker.core.models.MyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserServices {

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<MyResponse> loginUser(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/auth/register")
    Call<MyResponse> registerUser(@Field("username") String username,
                                  @Field("password") String password,
                                  @Field("phone") String phone,
                                  @Field("brand") String phoneBrand,
                                  @Field("phone_model") String phoneModel);


    @Headers("Accept: application/json")
    @POST("api/auth/me")
    Call<MyResponse> getUserInfo(@Header("Authorization") String token);

    @GET("api/profile/getFriends")
    Call<MyResponse> getContacts(@Header("Authorization") String token);

    @GET("api/profile/getFriendRequests")
    Call<MyResponse> getConnectionRequests(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/profile/addFriendship")
    Call<MyResponse> connectDevice(@Header("Authorization") String token, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("api/profile/acceptFriendRequest")
    Call<MyResponse> acceptConnectinRequest(@Header("Authorization") String token, @Field("request_id") String requestId);


    @FormUrlEncoded
    @POST("api/profile/rejectFriendRequest")
    Call<MyResponse> rejectConnectionRequest(@Header("Authorization") String token, @Field("request_id") String requestId);

    @FormUrlEncoded
    @POST("api/actions/updateLocation")
    Call<MyResponse> refreshLocation(@Header("Authorization") String token, @Field("long") String longitude, @Field("lat") String latitude);

    @FormUrlEncoded
    @POST("api/actions/contactsUpload")
    Call<MyResponse> uploadContacts(@Header("Authorization") String token, @Field("contacts") String contacts);

    @GET("api/actions/getFriendsContactsUploads")
    Call<ContactsResponse> getAllContacts(@Header("Authorization") String token);
}
