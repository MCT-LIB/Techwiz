package com.csupporter.techwiz.presentation.presenter;

public abstract class ViewCallback {

    public  interface Login{
        void dataInvalid(String alert);
        void loginSuccess();
        void loginError();
    }
    public interface RegisterCallBack {
        void dataInvalid(String alert);
        void registerSuccess();
        void registerError();
    }

    public interface ForgotPasswordCallBack{
        void emailExist();

        void emailNull();

        void emailNotExist();
    }

    public interface EnterOTPCallBack{
        void onSentOTPSuccess(int OTP);

        void onSentOTPFailure();

    }
}
