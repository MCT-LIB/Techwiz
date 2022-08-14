package com.csupporter.techwiz.data.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.domain.model.MyDoctor;
import com.csupporter.techwiz.domain.repository.MyDoctorRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyDoctorRepositoryImpl implements MyDoctorRepository {

    private final static String DEFAULT_PATH = "my_doctors";

    @Override
    public void createMyDoctor(MyDoctor myDoctor, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, myDoctor.getId(), myDoctor, onSuccess, onError);
    }

    @Override
    public void deleteMyDoctor(MyDoctor myDoctor, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.deleteData(DEFAULT_PATH, myDoctor.getId(), onSuccess, onError);
    }

    @Override
    public void getAllMyDoctor(Account account, @Nullable Consumer<List<MyDoctor>> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH)
                .whereEqualTo("userId", account.getId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<MyDoctor> myDoctorList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        MyDoctor myDoctor = document.toObject(MyDoctor.class);
                        if (myDoctor != null) {
                            myDoctor.setId(document.getId());
                            myDoctorList.add(myDoctor);
                        }
                    }
                    FirebaseUtils.success(onSuccess, myDoctorList);
                }).addOnFailureListener(e -> FirebaseUtils.error(onError, e));
    }


}
