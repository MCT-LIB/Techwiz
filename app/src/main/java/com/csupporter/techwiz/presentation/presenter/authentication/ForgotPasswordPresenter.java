package com.csupporter.techwiz.presentation.presenter.authentication;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.presentation.presenter.AuthenticationCallback;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class ForgotPasswordPresenter extends BasePresenter {

    public ForgotPasswordPresenter(BaseView baseView) {
        super(baseView);
    }

    public void checkEmailExist(String Email, AuthenticationCallback.ForgotPasswordCallBack callBack) {
        if (Email.trim().isEmpty()) {
            callBack.emailNull();
            return;
        }
        getBaseView().showLoading();
        DataInjection.provideRepository().account.findAccountByEmail(Email, account -> {
            if (account != null) {
                callBack.emailExist(account);
            } else {
                getBaseView().hideLoading();
                callBack.emailNotExist();
            }
        }, throwable -> {
            getBaseView().hideLoading();
            callBack.emailNotExist();
        });
    }

}
