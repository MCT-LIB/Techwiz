package com.csupporter.techwiz.presentation.presenter;

public abstract class MainViewCallBack {

    public interface NavUserInfoCallBack{
        void onLogOutSuccess();

        void onLogOutFailure();
    }
}
