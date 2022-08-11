package com.csupporter.techwiz.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.domain.model.Appointment;

public interface AppointmentRepository {

    void addAppointment(Appointment appointment,
                        @Nullable Consumer<Void> onSuccess,
                        @Nullable Consumer<Throwable> onError);

    void updateAppointment(Appointment appointment,
                           @Nullable Consumer<Void> onSuccess,
                           @Nullable Consumer<Throwable> onError);
}
