package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.Prescription;
import com.csupporter.techwiz.domain.model.PrescriptionDetail;
import com.csupporter.techwiz.domain.repository.PrescriptionDetailRepository;

public class PrescriptionDetailRepositoryImpl implements PrescriptionDetailRepository {

    private static final String DEFAULT_PATH = "prescription_details";

    @Override
    public void createPrescriptionDetail(PrescriptionDetail prescription, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, prescription.getId(), prescription, onSuccess, onError);
    }
}
