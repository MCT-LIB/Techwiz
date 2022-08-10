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

public class ForgotPasswordPresenter extends BasePresenter {

    public ForgotPasswordPresenter(BaseView baseView) {
        super(baseView);
    }

    public void checkEmailExist(String Email,ViewCallback.ForgotPasswordCallBack callBack){
        if(Email.trim().isEmpty()){
            callBack.emailNull();
            return;
        }
        DataInjection.provideRepository().account.checkEmailExits(Email, aBoolean -> {
            if (aBoolean){
                callBack.emailExist();
            }else {
                callBack.emailNotExist();
            }
        },null);
    }
}
