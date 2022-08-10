package com.csupporter.techwiz.presentation.presenter;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.csupporter.techwiz.di.DataInjection;
import com.google.gson.JsonObject;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterOTPPresenter extends BasePresenter {

    public EnterOTPPresenter(BaseView baseView){
        super(baseView);
    }

    public void sentOTP(String email, ViewCallback.EnterOTPCallBack callBack) {
        DataInjection.provideDataService().sendMailOtp(email, "Forgot Password", "The OTP authentication code of your is: ")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            int status = response.body().get("status").getAsInt();
                            // 200 is success
                            if (status == 200) {
                                int otp = response.body().get("data").getAsInt();
                                callBack.onSentOTPSuccess(otp);
                            } else {
                                callBack.onSentOTPFailure();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        callBack.onSentOTPFailure();
                    }
                });
    }
}
