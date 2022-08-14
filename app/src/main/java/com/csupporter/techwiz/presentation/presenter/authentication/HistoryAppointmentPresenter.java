package com.csupporter.techwiz.presentation.presenter.authentication;

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

    public void requestAppointments(int status, long date, @NonNull MainViewCallBack.GetAppointmentHistoryCallback callback) {
        DataInjection.provideRepository().appointment.getAppointmentByDateAndStatus(App.getApp().getAccount(), date, status,
                new Consumer<List<Appointment>>() {
                    int count;
                    List<AppointmentDetail> appointmentDetails;

                    @Override
                    public void accept(List<Appointment> appointments) {
                        appointmentDetails = new ArrayList<>();
                        for (Appointment appointment : appointments) {

                        }

                    }
                }, callback::onError);
    }
}
