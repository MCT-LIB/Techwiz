package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.domain.repository.HeathTrackingRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void getAllHealthTracking(Account account, @Nullable Consumer<List<HealthTracking>> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH)
                .whereEqualTo("userId", account.getId()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<HealthTracking> healthTrackingList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        HealthTracking health = document.toObject(HealthTracking.class);
                        if (health != null) {
                            health.setId(document.getId());
                            healthTrackingList.add(health);
                        }
                    }
                    FirebaseUtils.success(onSuccess, healthTrackingList);
                }).addOnFailureListener(e -> FirebaseUtils.error(onError, e));
    }


}
