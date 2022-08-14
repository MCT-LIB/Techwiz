package com.csupporter.techwiz.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.domain.model.Prescription;

public interface PrescriptionRepository {

    void createNewPrescription(Prescription prescription, @Nullable Consumer<Void> onSuccess,
                               @Nullable Consumer<Throwable> onError);
}
