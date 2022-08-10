package com.csupporter.techwiz.presentation.presenter;

import android.util.Patterns;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.regex.Pattern;

public class RegisterPresenter extends BasePresenter {

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";


    public RegisterPresenter(BaseView baseView) {
        super(baseView);
    }

    public void register(Account account, ViewCallback.RegisterCallBack callBack) {
        getBaseView().showLoading();
        verifyData(account, callBack);

        DataInjection.provideRepository().account.addAccount(account, unused ->
                {
                    getBaseView().hideLoading();
                    callBack.registerSuccess();
                },
                throwable -> {
                    getBaseView().hideLoading();
                    callBack.registerError();
                });
    }

    private void verifyData(Account account, ViewCallback.RegisterCallBack callBack) {
        if (account.getUserName().isEmpty() || account.getEmail().isEmpty() ||
                account.getFirstName().isEmpty() || account.getLastName().isEmpty() ||
                account.getPhone().isEmpty() || account.getAge() == 0 ||
                account.getPassword().isEmpty()) {
            getBaseView().hideLoading();
            callBack.dataInvalid("Input field must be fill !");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(account.getEmail()).matches()) {
            getBaseView().hideLoading();
            callBack.dataInvalid("Email is invalid !");
            return;
        }

        if (!Pattern.matches(PASSWORD_REGEX, account.getPassword())) {
            getBaseView().hideLoading();
            callBack.dataInvalid("Password is invalid !");
            return;
        }

        if (account.getAge() > 100 || account.getAge() < 0) {
            getBaseView().hideLoading();
            callBack.dataInvalid("Age not more than 100 and less than 0!");
            return;
        }
    }
}
