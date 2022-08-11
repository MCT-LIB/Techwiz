package com.csupporter.techwiz.presentation.presenter;

import android.util.Patterns;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.regex.Pattern;

public class LoginPresenter extends BasePresenter {

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

    public LoginPresenter(BaseView baseView) {
        super(baseView);
    }

    public void login(String userName, String password, ViewCallback.LoginCallback callback) {
        getBaseView().showLoading();

        if (verifyDataInputLogin(userName, password, callback)) {
            DataInjection.provideRepository().account.checkUserNameAndPassword(userName, password, new Consumer<Account>() {
                @Override
                public void accept(Account account) {
                    getBaseView().hideLoading();
                    DataInjection.provideSettingPreferences().setToken(account.getId());
                    callback.loginSuccess();
                }
            }, throwable -> {
                getBaseView().hideLoading();
                callback.loginError();
            });
        }
    }


    private boolean verifyDataInputLogin(String userName, String password, ViewCallback.LoginCallback callback) {
        if (userName.isEmpty() || password.isEmpty()) {
            callback.dataInvalid("Please complete the input field !");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
            getBaseView().hideLoading();
            callback.dataInvalid("Email is invalid !");
            return false;
        }

        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            callback.dataInvalid(
                    "Password must contain at least one uppercase letter, lowercase letter and number!");
            return false;
        }
        return true;
    }
}
