package com.csupporter.techwiz.presentation.presenter.authentication;

import android.util.Log;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.MyDoctor;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.ArrayList;
import java.util.List;

public class AddAppointmentPresenter extends BasePresenter {

    public AddAppointmentPresenter(BaseView baseView) {
        super(baseView);
    }

    public void getAllMyDoctor(MainViewCallBack.GetAllMyDoctorCallBack callBack) {
        Account acc = App.getApp().getAccount();
        getBaseView().showLoading();
        DataInjection.provideRepository().myDoctor.getAllMyDoctor(acc, new Consumer<List<MyDoctor>>() {
            int count;
            List<Account> doctorList = new ArrayList<>();

            @Override
            public void accept(List<MyDoctor> myDoctorList) {
                getBaseView().hideLoading();

                for (MyDoctor data : myDoctorList) {

                    DataInjection.provideRepository().account.findAccountById(data.getDoctorId(), account -> {

                        if (account != null) {
                            doctorList.add(account);
                        }
                        ++count;
                        Log.e("ddd", "accept: "+count + myDoctorList.size());

                        if (count == myDoctorList.size()) {
                            Log.e("ddd", "EQUAL: "+doctorList.size());

                            callBack.allMyDoctor(doctorList);
                        }

                    }, throwable -> {
                        ++count;
                        Log.e("ddd", "accept: "+count + myDoctorList.size());
                        if (count == myDoctorList.size()) {
                            getBaseView().hideLoading();
                        }
                    });
                }
            }
        }, throwable -> getBaseView().hideLoading());
    }


    public void createAppointment(){

    }

}
