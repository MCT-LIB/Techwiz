package com.csupporter.techwiz.presentation.presenter;

import com.csupporter.techwiz.domain.model.Account;

public abstract class ViewCallback {

    public enum ErrorTo {
        FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, CF_PASSWORD, NONE
    }

    public interface LoginCallback {
        void dataInvalid(String alert);

        void loginSuccess();

        void loginError();
    }

    public interface VerifyAccountCallBack {
        boolean isDispose();

        void dataInvalid(String alert, ErrorTo errorTo);

        void verified();
    }

    public interface RegisterCallBack {

        void registerSuccess();

        void registerError();
    }

    public interface ForgotPasswordCallBack {
        void emailExist(Account account);

        void emailNull();

        void emailNotExist();
    }

    public interface EnterOTPCallBack {
        void onSentOTPSuccess(int OTP);

        void onSentOTPFailure();
    }

    public interface ResetPasswordCallBack {
        void onSuccess(String pw);

        void onFailure();

        void onEmptyValue();
    }


}
