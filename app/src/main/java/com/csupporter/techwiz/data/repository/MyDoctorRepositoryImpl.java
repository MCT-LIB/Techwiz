package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.MyDoctor;
import com.csupporter.techwiz.domain.repository.MyDoctorRepository;

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

}
