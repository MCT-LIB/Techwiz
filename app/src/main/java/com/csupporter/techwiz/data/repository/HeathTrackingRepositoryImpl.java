package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.domain.repository.HeathTrackingRepository;

public class HeathTrackingRepositoryImpl implements HeathTrackingRepository {

    private final static String DEFAULT_PATH = "health_tracking";

    @Override
    public void addTracking(HealthTracking tracking, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, tracking.getId(), tracking, onSuccess, onError);
    }

    @Override
    public void updateTracking(HealthTracking tracking, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, tracking.getId(), tracking, onSuccess, onError);
    }
}
