package com.csupporter.techwiz.presentation.presenter.main;

import com.csupporter.techwiz.di.DataInjection;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class NavUserInfoPresenter extends BasePresenter {

    public NavUserInfoPresenter(BaseView baseView) {
        super(baseView);
    }

    public void logOut(){
        DataInjection.provideSettingPreferences().setToken(null);
    }
}
