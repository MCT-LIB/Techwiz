package com.csupporter.techwiz.presentation.presenter.authentication;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.model.AppointmentSchedule;
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
        DataInjection.provideRepository().appointment.getAppointmentByDateAndStatus(App.getApp().getAccount(), date, status,
                new Consumer<List<Appointment>>() {
                    int count;
                    List<AppointmentDetail> appointmentDetails;

                    @Override
                    public void accept(List<Appointment> appointments) {
                        appointmentDetails = new ArrayList<>();
                        if (appointments.isEmpty()) {
                            callback.onGetHistorySuccess(appointmentDetails);
                            return;
                        }
                        for (Appointment appointment : appointments) {
                            DataInjection.provideRepository().account
                                    .findAccountById(App.getApp().getAccount().isUser() ? appointment.getDoctorId() : appointment.getUserId(), fillAcc -> {
                                        ++count;
                                        if (fillAcc != null) {
                                            AppointmentDetail detail = new AppointmentDetail(appointment, fillAcc);
                                            appointmentDetails.add(detail);
                                        }
                                        if (count == appointments.size()) {
                                            callback.onGetHistorySuccess(appointmentDetails);
                                        }
                                    }, throwable -> {
                                        ++count;
                                        if (count == appointments.size()) {
                                            callback.onGetHistorySuccess(appointmentDetails);
                                        }
                                    });
                        }
                    }
                }, callback::onError);
    }

    public void createAppointment(Appointment appointment, AppointmentSchedule schedule, @NonNull MainViewCallBack.CreateAppointmentCallback callback) {
        getBaseView().showLoading();
        DataInjection.provideRepository().appointment.addAppointment(appointment, schedule, unused -> {
            getBaseView().hideLoading();
            callback.onCreateSuccess();
        }, throwable -> {
            getBaseView().hideLoading();
            callback.onError(throwable);
        });
    }
}
