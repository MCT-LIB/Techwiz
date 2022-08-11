package com.csupporter.techwiz.presentation.presenter;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class ForgotPasswordPresenter extends BasePresenter {

    public ForgotPasswordPresenter(BaseView baseView) {
        super(baseView);
    }

    public void checkEmailExist(String Email, ViewCallback.ForgotPasswordCallBack callBack) {
        if (Email.trim().isEmpty()) {
            callBack.emailNull();
            return;
        }
        DataInjection.provideRepository().account.findAccountByEmail(Email, account -> {
            if (account != null) {
                callBack.emailExist(account);
            } else {
                callBack.emailNotExist();
            }
        }, throwable -> callBack.emailNotExist());
    }

}
