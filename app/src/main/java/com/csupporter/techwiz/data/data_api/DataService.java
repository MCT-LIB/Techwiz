package com.csupporter.techwiz.data.data_api;

import com.google.gson.JsonObject;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DataService {

    @FormUrlEncoded
    @POST("mailer/send_otp")
    Call<JsonObject> sendMailOtp(@Nullable @Field("send_name") String fromName,
                                 @Nullable @Field("receive_name") String toName,
                                 @Field("type") int type,
                                 @Field("email") String email);


}
