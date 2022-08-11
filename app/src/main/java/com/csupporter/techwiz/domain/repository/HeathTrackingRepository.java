package com.csupporter.techwiz.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.domain.model.HealthTracking;

public interface HeathTrackingRepository {

    void addTracking(HealthTracking appointment,
                        @Nullable Consumer<Void> onSuccess,
                        @Nullable Consumer<Throwable> onError);

    void updateTracking(HealthTracking appointment,
                           @Nullable Consumer<Void> onSuccess,
                           @Nullable Consumer<Throwable> onError);
}
