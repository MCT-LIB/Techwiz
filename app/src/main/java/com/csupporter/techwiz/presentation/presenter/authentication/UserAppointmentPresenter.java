package com.csupporter.techwiz.presentation.presenter.authentication;


import android.util.Log;
import android.widget.Toast;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.ArrayList;
import java.util.List;


public class UserAppointmentPresenter extends BasePresenter {

    public UserAppointmentPresenter(BaseView baseView) {
        super(baseView);
    }

    public void getAllDoctor(MainViewCallBack.UserAppointmentCallBack callBack) {
        getBaseView().showLoading();
        DataInjection.provideRepository().account.getAllDoctor(accounts -> {
            getBaseView().hideLoading();
            callBack.doctorList(accounts);
        }, throwable -> getBaseView().hideLoading());
    }

    public void getDoctorsByDepartment(int department,MainViewCallBack.UserAppointmentCallBack callBack) {
        getBaseView().showLoading();
        DataInjection.provideRepository().account.filterDoctorByDepartment(department, accounts -> {
            getBaseView().hideLoading();
        }, throwable -> getBaseView().hideLoading());
    }

    public void getAllAppointmentOfUserByDate(Account account, long date, MainViewCallBack.UserAppointmentCallBack callBack) {
        getBaseView().showLoading();
        DataInjection.provideRepository().appointment.getAppointmentsByDate(account, date, new Consumer<List<Appointment>>() {
            int count;
            List<AppointmentDetail> appointmentDetails = new ArrayList<>();

            @Override
            public void accept(List<Appointment> appointments) {
                getBaseView().hideLoading();
                for (Appointment appointment : appointments) {
                    DataInjection.provideRepository().account
                            .findAccountById(account.isUser() ? appointment.getDoctorId() : appointment.getUserId(), new Consumer<Account>() {
                                @Override
                                public void accept(Account fillAcc) {
                                    ++count;
                                    if (fillAcc != null) {
                                        AppointmentDetail detail = new AppointmentDetail(appointment, fillAcc);
                                        appointmentDetails.add(detail);
                                    }
                                    if (count == appointments.size()) {
                                        callBack.getNameAcc(account);
                                    }
                                }
                            }, throwable -> {
                                ++count;
                                if (count == appointments.size()) {
                                    getBaseView().hideLoading();
                                }
                            });
                }
            }
        }, throwable -> {
            Log.e("ddd", "accept: ", throwable);
            callBack.onFailure();
        });
    }
}
