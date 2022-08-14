package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.domain.repository.PrescriptionRepository;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    private static final String DEFAULT_PATH = "prescription";

    @Override
    public void createNewPrescription(Prescription prescription, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, prescription.getId(), prescription, onSuccess, onError);
    }
}
