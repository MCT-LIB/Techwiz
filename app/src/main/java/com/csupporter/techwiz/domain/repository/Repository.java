package com.csupporter.techwiz.domain.repository;

public abstract class Repository {

    public final ImageManager imageManager;
    public final AccountRepository account;
    public final AppointmentRepository appointment;
    public final HeathTrackingRepository heathTracking;

    public Repository(ImageManager imageManager, AccountRepository account, AppointmentRepository appointment, HeathTrackingRepository heathTracking) {
        this.imageManager = imageManager;
        this.account = account;
        this.appointment = appointment;
        this.heathTracking  = heathTracking;
    }
}
