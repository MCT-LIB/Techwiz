package com.csupporter.techwiz.presentation.presenter.authentication;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.domain.model.PrescriptionDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.List;

public class ListPrescriptionDetailPresenter extends BasePresenter {

    public ListPrescriptionDetailPresenter(BaseView baseView) {
        super(baseView);
    }

    public void getAllPrescriptionDetail(Prescription prescription, MainViewCallBack.ListPrescriptionDetailCallback callback) {
        getBaseView().showLoading();
        DataInjection.provideRepository().listPrescriptionDetail
                .getAllPrescriptionDetail(prescription, prescriptionDetailList -> {
                    getBaseView().hideLoading();
                    callback.getAllListPrescription(prescriptionDetailList);
                }, throwable -> {
                    getBaseView().hideLoading();
                });
    }
}
