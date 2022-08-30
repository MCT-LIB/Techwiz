package com.csupporter.techwiz.presentation.presenter.user;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.model.AppointmentSchedule;
import com.csupporter.techwiz.domain.model.MyDoctor;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.baseui.BasePresenter;
import com.mct.components.baseui.BaseView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NavSearchPresenter extends BasePresenter {

    private final MainViewCallBack.SearchCallback callback;

    public NavSearchPresenter(BaseView baseView, MainViewCallBack.SearchCallback callback) {
        super(baseView);
        this.callback = callback;
    }

    public void searchDoctor(String keySearch) {
        getBaseView().showLoading();
        DataInjection.provideRepository().account.findAccountsByNameAndType(keySearch, Account.TYPE_DOCTOR, accounts -> {
            getBaseView().hideLoading();
            postMain(() -> callback.onSearchSuccess(accounts, Account.class));
        }, throwable -> {
            getBaseView().hideLoading();
            callback.onError(throwable);
        });
    }

    public void searchPrescription(String keySearch) {
        getBaseView().showLoading();
        DataInjection.provideRepository().prescription.findPrescriptionByName(App.getApp().getAccount(), keySearch, prescriptions -> {
            getBaseView().hideLoading();
            postMain(() -> callback.onSearchSuccess(prescriptions, Prescription.class));
        }, throwable -> {
            getBaseView().hideLoading();
            callback.onError(throwable);
        });
    }

    public void searchAppointment(long date) {
        List<Integer> status = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        getBaseView().showLoading();
        DataInjection.provideRepository().appointment.getAppointmentByDateAndStatus(App.getApp().getAccount(), date, status,
                new Consumer<List<Appointment>>() {
                    int count;
                    List<AppointmentDetail> appointmentDetails;

                    @Override
                    public void accept(List<Appointment> appointments) {
                        appointmentDetails = new ArrayList<>();
                        if (appointments.isEmpty()) {
                            getBaseView().hideLoading();
                            callback.onSearchSuccess(appointmentDetails, Appointment.class);
                            return;
                        }
                        for (Appointment appointment : appointments) {
                            DataInjection.provideRepository().account
                                    .findAccountById(App.getApp().getAccount().isUser() ? appointment.getDoctorId() : appointment.getUserId(), fillAcc -> {
                                        if (fillAcc != null) {
                                            AppointmentDetail detail = new AppointmentDetail(appointment, fillAcc);
                                            appointmentDetails.add(detail);
                                        }
                                        increaseAndCheck(appointments.size());
                                    }, throwable -> increaseAndCheck(appointments.size()));
                        }
                    }

                    private void increaseAndCheck(int size) {
                        ++count;
                        if (count == size) {
                            getBaseView().hideLoading();
                            callback.onSearchSuccess(appointmentDetails, Appointment.class);
                        }
                    }
                }, throwable -> {
                    getBaseView().hideLoading();
                    callback.onError(throwable);
                });
    }

    public void createMyDoctor(@NonNull Account doctor, MainViewCallBack.AddMyDoctor callBack) {
        Account user = App.getApp().getAccount();
        String userId = user.getId();
        String doctorId = doctor.getId();

        DataInjection.provideRepository().myDoctor.hasLinked(userId, doctorId, myDoctor -> {
            if (myDoctor == null) {
                MyDoctor dr = new MyDoctor(FirebaseUtils.uniqueId(), userId, doctorId);
                DataInjection.provideRepository().myDoctor.createMyDoctor(dr, unused -> {
                    callBack.addMyDoctorSuccess(doctor);
                }, throwable -> {
                    callBack.addMyDoctorFail();
                });
            } else {
                callBack.addMyDoctorSuccess(doctor);
            }
        }, throwable -> callBack.addMyDoctorFail());

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

    public void updateAppointment(@NonNull Appointment appointment, boolean isConfirm, MainViewCallBack.UpdateAppointmentCallback callback) {
        int currentStatus = appointment.getStatus();
        if (currentStatus != 0 && currentStatus != 1) {
            return;
        }
        if (isConfirm && currentStatus == 1) {
            return;
        }
        int newStatus;
        getBaseView().showLoading();
        if (isConfirm) {
            newStatus = 1;
        } else {
            boolean isUser = App.getApp().getAccount().isUser();
            if (isUser) {
                newStatus = 4;
            } else {
                if (currentStatus == 0) {
                    newStatus = 2;
                } else {
                    newStatus = 3;
                }
            }
        }
        appointment.setStatus(newStatus);
        AppointmentSchedule schedule = new AppointmentSchedule();
        schedule.setId(appointment.getId());
        schedule.setStatus(newStatus);
        DataInjection.provideRepository().appointment.updateAppointment(appointment, schedule, unused -> {
            getBaseView().hideLoading();
            callback.onCreateSuccess();
        }, throwable -> {
            getBaseView().hideLoading();
            callback.onError(throwable);
        });
    }

}
