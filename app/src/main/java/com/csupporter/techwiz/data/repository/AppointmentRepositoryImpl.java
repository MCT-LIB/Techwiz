package com.csupporter.techwiz.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.domain.repository.AppointmentRepository;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void getAppointments(@NonNull Account account, @Nullable Consumer<List<Appointment>> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH)
                .whereEqualTo(account.isUser() ? "userId" : "doctorId", account.getId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Appointment> appointments = new ArrayList<>();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Appointment appointment = snapshot.toObject(Appointment.class);
                        if (appointment != null) {
                            appointment.setId(snapshot.getId());
                            appointments.add(appointment);
                        }
                    }
                    FirebaseUtils.success(onSuccess, appointments);
                }).addOnFailureListener(e -> FirebaseUtils.error(onError, e));
    }
}
