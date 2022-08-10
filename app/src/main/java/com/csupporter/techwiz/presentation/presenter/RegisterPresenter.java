package com.csupporter.techwiz.presentation.presenter;

import com.csupporter.techwiz.domain.model.Account;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class RegisterPresenter extends BasePresenter {

    public RegisterPresenter(BaseView baseView) {
        super(baseView);
    }
    public void register(Account account, ViewCallback.RegisterCallBack callBack){
        getBaseView().showLoading();

    }
}
