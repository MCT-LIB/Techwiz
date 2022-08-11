package com.csupporter.techwiz.domain.repository;

public abstract class Repository {

    public final AccountRepository account;
    public final AppointmentRepository appointment;

    public Repository(AccountRepository account, AppointmentRepository appointment) {
        this.account = account;
        this.appointment = appointment;
    }
}
