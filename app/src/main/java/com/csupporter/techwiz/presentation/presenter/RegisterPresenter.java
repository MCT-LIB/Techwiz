package com.csupporter.techwiz.presentation.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.ViewCallback.ErrorTo;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.regex.Pattern;

public class RegisterPresenter extends BasePresenter {

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";


    public RegisterPresenter(BaseView baseView) {
        super(baseView);
    }

    public void register(Account account, String confPass, ViewCallback.RegisterCallBack callBack) {
        getBaseView().showLoading();
        if (!isValidateOk(account, confPass, callBack)) {
            getBaseView().hideLoading();
            return;
        }
        DataInjection.provideRepository().account.addAccount(account,
                unused -> {
                    getBaseView().hideLoading();
                    callBack.registerSuccess();
                }, throwable -> {
                    getBaseView().hideLoading();
                    callBack.registerError();
                });
    }

    private boolean isValidateOk(@NonNull Account account, String confPass, ViewCallback.RegisterCallBack callBack) {
        if (TextUtils.isEmpty(account.getFirstName())) {
            callBack.dataInvalid("Please enter your first name", ErrorTo.FIRST_NAME);
            return false;
        }
        if (TextUtils.isEmpty(account.getEmail())) {
            callBack.dataInvalid("Please enter your email", ErrorTo.EMAIL);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(account.getEmail()).matches()) {
            callBack.dataInvalid("Email is invalid!", ErrorTo.EMAIL);
            return false;
        }
        if (TextUtils.isEmpty(account.getPassword())) {
            callBack.dataInvalid("Please enter your password", ErrorTo.PASSWORD);
            return false;
        }
        if (!Pattern.matches(PASSWORD_REGEX, account.getPassword())) {
            callBack.dataInvalid("Password at least one number, one lowercase letter, one uppercase letter and greater than or equal to 8 characters", ErrorTo.PASSWORD);
            return false;
        }
        if (TextUtils.isEmpty(confPass)) {
            callBack.dataInvalid("Please confirm your password", ErrorTo.CF_PASSWORD);
            return false;
        }
        if (!Pattern.matches(PASSWORD_REGEX, confPass)) {
            callBack.dataInvalid("Password at least one number, one lowercase letter, one uppercase letter and greater than or equal to 8 characters", ErrorTo.CF_PASSWORD);
            return false;
        }
        if (!account.getPassword().equals(confPass)) {
            callBack.dataInvalid("Password and confirm password are not the same", ErrorTo.NONE);
            return false;
        }
        return true;
    }
}
