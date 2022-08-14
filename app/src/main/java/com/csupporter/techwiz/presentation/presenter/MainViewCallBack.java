package com.csupporter.techwiz.presentation.presenter;

import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.presentation.presenter.authentication.HealthyTrackingPresenter;

import java.util.List;

public abstract class MainViewCallBack {

    public interface NavUserInfoCallBack {

    }

    public interface UserHomeCallBack {
        void listAppointment(List<Appointment> appointmentList);
    }

    public interface UserAppointmentCallBack {
        void doctorList(List<Account> accounts);

        void getNameAcc(Account account);

        void onFailure();
    }

    public interface HealthTrackingCallBack {
        void addHealthTrackingSuccess(HealthTracking healthTracking);

        void addHealthTrackingFail(String message);

        void onGetDataSuccess(List<HealthTracking> trackingList);

        void onGetDataFailure();

    }

    public interface UpdateTrackCallBack{
        void onSuccess();
        void onFailure();
    }

    public interface DeleteTrackCallBack{
        void onDeleteSuccess();
        void onDeleteFailure();
    }
}
