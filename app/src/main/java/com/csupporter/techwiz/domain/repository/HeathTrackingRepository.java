package com.csupporter.techwiz.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.HealthTracking;

import java.util.List;

public interface HeathTrackingRepository {

    void addTracking(HealthTracking appointment,
                        @Nullable Consumer<Void> onSuccess,
                        @Nullable Consumer<Throwable> onError);

    void updateTracking(HealthTracking appointment,
                           @Nullable Consumer<Void> onSuccess,
                           @Nullable Consumer<Throwable> onError);
    void getAllHealthTracking(Account account,@Nullable Consumer<List<HealthTracking>> onSuccess,
                      @Nullable Consumer<Throwable> onError);
}
