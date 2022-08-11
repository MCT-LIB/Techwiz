package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.repository.AppointmentRepository;

public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final static String DEFAULT_PATH = "appointments";

    @Override
    public void addAppointment(Appointment appointment, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, appointment.getId(), appointment, onSuccess, onError);
    }

    @Override
    public void updateAppointment(Appointment appointment, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, appointment.getId(), appointment, onSuccess, onError);
    }



}
