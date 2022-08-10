package com.csupporter.techwiz.data.data_api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DataService {

    @FormUrlEncoded
    @POST("mailer/send_otp")
    Call<JsonObject> sendMailOtp(@Field("email") String email,
                                 @Field("title") String title,
                                 @Field("content") String content);


}
