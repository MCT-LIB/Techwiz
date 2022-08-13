package com.csupporter.techwiz.presentation.presenter.authentication;

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
