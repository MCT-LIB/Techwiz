package com.csupporter.techwiz.presentation.presenter.authentication;


import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;


public class UserAppointmentPresenter extends BasePresenter {


    public UserAppointmentPresenter(BaseView baseView) {
        super(baseView);
    }

    public void getAllDoctor(MainViewCallBack.UserAppointmentCallBack callBack){
        getBaseView().showLoading();
        DataInjection.provideRepository().account.getAllDoctor(accounts -> {
            getBaseView().hideLoading();
            callBack.doctorList(accounts);
        }, throwable -> getBaseView().hideLoading());
    }
}
