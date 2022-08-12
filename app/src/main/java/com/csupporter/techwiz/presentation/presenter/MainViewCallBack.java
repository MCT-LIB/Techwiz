package com.csupporter.techwiz.presentation.presenter;

import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;

import java.util.List;

public abstract class MainViewCallBack {

    public interface NavUserInfoCallBack {
        void requestPermissionSuccess();

        void notRequestPermission();
    }

    public interface UserHomeCallBack {
        void listAppointment(List<Appointment> appointmentList);

    }

    public interface UserAppointmentCallBack {
        void doctorList(List<Account> accounts);
    }

}
