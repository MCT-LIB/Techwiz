package com.csupporter.techwiz.presentation.presenter.authentication;


import android.util.Log;
import android.widget.Toast;

import androidx.core.util.Consumer;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.model.MyDoctor;
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

    public void getDoctorsByDepartment(int department, MainViewCallBack.UserAppointmentCallBack callBack) {
        getBaseView().showLoading();
        Account account = App.getApp().getAccount();

        DataInjection.provideRepository().myDoctor.getAllMyDoctor(account, myDoctorList -> {
            List<String> doctorsId = new ArrayList<>();
            for (MyDoctor doctor : myDoctorList) {
                doctorsId.add(doctor.getDoctorId());
            }
            DataInjection.provideRepository().account.getAllDoctorNotFavorite(doctorsId, accounts -> {
                if (department == -1) {
                    for (int i = accounts.size() - 1; i >= 0; i--) {
                        if (accounts.get(i).getDepartment() == -1) {
                            accounts.remove(i);
                        }
                    }
                } else {
                    for (int i = accounts.size() - 1; i >= 0; i--) {
                        if (accounts.get(i).getDepartment() != department) {
                            accounts.remove(i);
                        }
                    }
                }
                getBaseView().hideLoading();
                callBack.onRequestSuccess(accounts);
            }, throwable -> getBaseView().hideLoading());
        }, throwable -> getBaseView().hideLoading());
//        if (department != -1) {
//            DataInjection.provideRepository().account.filterDoctorByDepartment(department, accounts -> {
//                getBaseView().hideLoading();
//                callBack.onRequestSuccess(accounts);
//            }, throwable -> getBaseView().hideLoading());
//
//        } else {
//            DataInjection.provideRepository().account.getAllDoctor(accounts -> {
//                getBaseView().hideLoading();
//                callBack.onRequestSuccess(accounts);
//            }, throwable -> getBaseView().hideLoading());
//        }
    }

    public void getAllAppointmentOfUserByDate(Account account, long date, MainViewCallBack.UserAppointmentCallBack callBack) {
//        getBaseView().showLoading();
//        DataInjection.provideRepository().appointment.getAppointmentsByDate(account, date, new Consumer<List<Appointment>>() {
//            int count;
//            List<AppointmentDetail> appointmentDetails = new ArrayList<>();
//
//            @Override
//            public void accept(List<Appointment> appointments) {
//                getBaseView().hideLoading();
//                for (Appointment appointment : appointments) {
//                    DataInjection.provideRepository().account
//                            .findAccountById(account.isUser() ? appointment.getDoctorId() : appointment.getUserId(), new Consumer<Account>() {
//                                @Override
//                                public void accept(Account fillAcc) {
//                                    ++count;
//                                    if (fillAcc != null) {
//                                        AppointmentDetail detail = new AppointmentDetail(appointment, fillAcc);
//                                        appointmentDetails.add(detail);
//                                    }
//                                    if (count == appointments.size()) {
//                                        callBack.getNameAcc(account);
//                                    }
//                                }
//                            }, throwable -> {
//                                ++count;
//                                if (count == appointments.size()) {
//                                    getBaseView().hideLoading();
//                                }
//                            });
//                }
//            }
//        }, throwable -> {
//            Log.e("ddd", "accept: ", throwable);
//            callBack.onFailure();
//        });
    }

    public void createMyDoctor(Account doctor, MainViewCallBack.AddMyDoctor callBack) {
        Account user = App.getApp().getAccount();

        MyDoctor myDoctor = new MyDoctor(FirebaseUtils.uniqueId(), user.getId(), doctor.getId());
        getBaseView().showLoading();

        DataInjection.provideRepository().myDoctor.createMyDoctor(myDoctor, unused -> {

            getBaseView().hideLoading();
            callBack.addMyDoctorSuccess(user);

        }, throwable -> {

            getBaseView().hideLoading();
            callBack.addMyDoctorFail();

        });
    }

    public void deleteMyDoctor(Account doctor, MainViewCallBack.DeleteMyDoctor callBack) {

        Account user = App.getApp().getAccount();
        MyDoctor myDoctor = new MyDoctor(FirebaseUtils.uniqueId(), user.getId(), doctor.getId());

        getBaseView().showLoading();
        DataInjection.provideRepository().myDoctor.deleteMyDoctor(myDoctor, unused -> {

            getBaseView().showLoading();
            callBack.deleteMyDoctorSuccess();

        }, throwable -> {

            getBaseView().showLoading();
            callBack.deleteMyDoctorFail();
        });
    }

    public void getAllMyDoctor(MainViewCallBack.UserAppointmentCallBack callBack) {
        Account user = App.getApp().getAccount();
        getBaseView().showLoading();
        DataInjection.provideRepository().myDoctor.getAllMyDoctor(user, new Consumer<List<MyDoctor>>() {
            @Override
            public void accept(List<MyDoctor> myDoctors) {
                getBaseView().hideLoading();
            }
        }, throwable -> {
            getBaseView().hideLoading();
        });
    }
}
