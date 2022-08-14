package com.csupporter.techwiz.domain.repository;

import com.google.android.gms.common.images.ImageManager;

public abstract class Repository {

    public final ImageManager imageManager;
    public final AccountRepository account;
    public final AppointmentRepository appointment;
    public final HeathTrackingRepository heathTracking;
    public final MyDoctorRepository myDoctor;

    public Repository(ImageManager imageManager, AccountRepository account, AppointmentRepository appointment, HeathTrackingRepository heathTracking, MyDoctorRepository doctorRepository) {
        this.imageManager = imageManager;
        this.account = account;
        this.appointment = appointment;
        this.heathTracking  = heathTracking;
        this.myDoctor = doctorRepository;
    }
}
