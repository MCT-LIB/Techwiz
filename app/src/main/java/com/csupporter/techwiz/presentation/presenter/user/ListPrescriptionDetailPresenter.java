package com.csupporter.techwiz.presentation.presenter.user;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.domain.model.PrescriptionDetail;
import com.csupporter.techwiz.domain.repository.ImageManager;
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

    public void getUserCreatedPrescription(@NonNull Prescription prescription, MainViewCallBack.GetUserCreatedPrescription callback) {
        getBaseView().showLoading();

        DataInjection.provideRepository().account
                .findAccountById(prescription.getDoctorId() == null ? prescription.getUserId() : prescription.getDoctorId(), account -> {
                    getBaseView().hideLoading();
                    callback.getUserCreatedPrescription(account);
                }, throwable -> getBaseView().hideLoading());

    }

    public void deletePrescription(@NonNull PrescriptionDetail prescriptionDetail, int pos, MainViewCallBack.DeletePrescriptionDetail callback) {
        getBaseView().showLoading();
        DataInjection.provideRepository().imageManager.delete(ImageManager.Type.MEDICINE, prescriptionDetail.getId());
        DataInjection.provideRepository().prescriptionDetail.deletePrescriptionDetail(prescriptionDetail, unused -> {
            getBaseView().hideLoading();
            callback.deletePrescriptionDetailSuccess(prescriptionDetail, pos);
        }, throwable -> {
            getBaseView().hideLoading();
            callback.deletePrescriptionDetailFail();
        });
    }

}
