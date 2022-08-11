package com.csupporter.techwiz.presentation.presenter.authentication;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.AuthenticationCallback;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class ResetPasswordPresenter extends BasePresenter {

    public ResetPasswordPresenter(BaseView baseView) {
        super(baseView);
    }

    public void checkReEnterPassword(String newPass, String reNewPass, AuthenticationCallback.ResetPasswordCallBack callBack){
        if (newPass.equals("") || reNewPass.equals("")){
            callBack.onEmptyValue();
            return;
        }
        if (newPass.equals(reNewPass)){
            callBack.onSuccess(newPass);
        }else {
            callBack.onFailure();
        }
    }

    public void resetPassword(Account account, AuthenticationCallback.ChangePassCallback callback){
        DataInjection.provideRepository().account.updateAccount(account, new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                callback.onSuccess(account);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                callback.onFailure();
            }
        });
    }
}
