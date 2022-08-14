package com.csupporter.techwiz.presentation.presenter.authentication;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.csupporter.techwiz.presentation.view.dialog.AddNewPrescriptionDialog;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

public class AllPrescriptionPresenter extends BasePresenter {

    public AllPrescriptionPresenter(BaseView baseView) {
        super(baseView);
    }
    public void createPrescription(Prescription prescription, AddNewPrescriptionDialog dialog, MainViewCallBack.CreatePrescriptionCallBack callBack){
        getBaseView().showLoading();
        DataInjection.provideRepository().prescription.createNewPrescription(prescription, new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                getBaseView().hideLoading();
                dialog.dismiss();
                callBack.onCreateSuccess(prescription);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                getBaseView().hideLoading();
                dialog.dismiss();
                callBack.onCreateFail();
            }
        });
    }
}
