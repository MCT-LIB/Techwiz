package com.csupporter.techwiz.presentation.presenter.user;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class ProfilePresenter extends BasePresenter {

    public ProfilePresenter(BaseView baseView) {
        super(baseView);
    }

    public void updateProfile(Account account, MainViewCallBack.UpdateProfileCallback callback) {
        getBaseView().showLoading();
        DataInjection.provideRepository().account.updateAccount(account, unused -> {
            getBaseView().hideLoading();
            if (callback != null) {
                callback.onSuccess();
            }
        }, throwable -> {
            getBaseView().hideLoading();
            if (callback != null) {
                callback.onError(throwable);
            }
        });
    }
}
