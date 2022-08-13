package com.csupporter.techwiz.domain.repository;

public abstract class Repository {

    public final AccountRepository account;
    public final AppointmentRepository appointment;
    public final HeathTrackingRepository heathTracking;

    public Repository(AccountRepository account, AppointmentRepository appointment,HeathTrackingRepository heathTracking) {
        this.account = account;
        this.appointment = appointment;
        this.heathTracking  = heathTracking;
    }
}
