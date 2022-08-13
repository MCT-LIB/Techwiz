package com.csupporter.techwiz.presentation.presenter.authentication;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.di.DataInjection;
import android.util.Log;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.List;

public class HealthyTrackingPresenter extends BasePresenter {


    public HealthyTrackingPresenter(BaseView baseView) {
        super(baseView);
    }

    public void addNewHealthTracking(String txtHeight, String txtWeight, String txtHeartBeat, String txtBloodSugar, String txtBloodPressure, String txtNote, MainViewCallBack.HealthTrackingCallBack callBack) {

        HealthTracking healthTracking = verifyDataInput(txtWeight, txtHeight, txtBloodSugar, txtBloodPressure, txtHeartBeat, txtNote, callBack);

        if (healthTracking != null) {
            DataInjection.provideRepository().heathTracking.addTracking(healthTracking, unused -> {

                getBaseView().hideLoading();
                callBack.addHealthTrackingSuccess();

            }, throwable -> {
                getBaseView().hideLoading();
                callBack.addHealthTrackingFail("Add new health tracking fail !");
            });
        }
    }

    public HealthTracking verifyDataInput(String weight, String height, String bloodSugar, String bloodPressure,
                                          String heartBeat, String note, MainViewCallBack.HealthTrackingCallBack callBack) {
        Account account = App.getApp().getAccount();

        if (height.isEmpty() || weight.isEmpty() ||
                bloodSugar.isEmpty() || bloodPressure.isEmpty() ||
                heartBeat.isEmpty()) {

            getBaseView().hideLoading();
            callBack.addHealthTrackingFail("Please complete all information !");


        } else {
            HealthTracking healthTracking = new HealthTracking();

            healthTracking.setId(FirebaseUtils.uniqueId());
            healthTracking.setUserId(account.getId());
            healthTracking.setHeight(Float.parseFloat(height));
            healthTracking.setWeight(Float.parseFloat(weight));
            healthTracking.setHeartbeat(Float.parseFloat(heartBeat));
            healthTracking.setBloodSugar(Float.parseFloat(bloodSugar));
            healthTracking.setBloodPressure(Float.parseFloat(bloodPressure));
            healthTracking.setOther(note);
            healthTracking.setCreateAt(System.currentTimeMillis());
            return healthTracking;
        }
        return null;
    public void getListTrack(MainViewCallBack.HealthTrackingCallBack callBack){
        DataInjection.provideRepository().heathTracking.getAllHealthTracking(App.getApp().getAccount(), new Consumer<List<HealthTracking>>() {
            @Override
            public void accept(List<HealthTracking> healthTrackings) {
                Log.d("ddd", "accept: " + healthTrackings);
                if (healthTrackings != null){
                    Log.d("ddd", "accept: " + healthTrackings.size());
                }
                callBack.onGetDataSuccess(healthTrackings);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Log.d("ddd", "accept: ", throwable);
                callBack.onGetDataFailure();
            }
        });
    }
}
