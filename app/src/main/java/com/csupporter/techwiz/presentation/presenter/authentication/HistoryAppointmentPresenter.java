package com.csupporter.techwiz.presentation.presenter.authentication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAppointmentPresenter extends BasePresenter {

    public HistoryAppointmentPresenter(BaseView baseView) {
        super(baseView);
    }

    public void requestAppointments(List<Integer> status, long date, @NonNull MainViewCallBack.GetAppointmentHistoryCallback callback) {
        getBaseView().showLoading();
        DataInjection.provideRepository().appointment.getAppointmentByDateAndStatus(App.getApp().getAccount(), date, status,
                new Consumer<List<Appointment>>() {
                    int count;
                    List<AppointmentDetail> appointmentDetails;

                    @Override
                    public void accept(List<Appointment> appointments) {
                        if(appointments.isEmpty()){
                            getBaseView().hideLoading();
                            return;
                        }
                        appointmentDetails = new ArrayList<>();
                        for (Appointment appointment : appointments) {
                            DataInjection.provideRepository().account
                                    .findAccountById(App.getApp().getAccount().isUser() ? appointment.getDoctorId() : appointment.getUserId(), fillAcc -> {
                                        ++count;
                                        if (fillAcc != null) {
                                            AppointmentDetail detail = new AppointmentDetail(appointment, fillAcc);
                                            appointmentDetails.add(detail);
                                        }
                                        if (count == appointments.size()) {
                                            getBaseView().hideLoading();
                                            callback.onGetHistorySuccess(appointmentDetails);
                                        }
                                    }, throwable -> {
                                        ++count;
                                        if (count == appointments.size()) {
                                            getBaseView().hideLoading();
                                            callback.onGetHistorySuccess(appointmentDetails);
                                        }
                                    });
                        }
                    }
                }, callback::onError);
    }
}
