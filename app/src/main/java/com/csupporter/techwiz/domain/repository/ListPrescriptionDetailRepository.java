package com.csupporter.techwiz.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.domain.model.Prescription;

import java.util.List;

public interface ListPrescriptionDetailRepository {
    void getAllPrescriptionDetail(Prescription prescription,
                                  @Nullable Consumer<List<HealthTracking>> onSuccess,
                                  @Nullable Consumer<Throwable> onError);
}
