package com.csupporter.techwiz.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.domain.model.MyDoctor;


public interface MyDoctorRepository {
    void createMyDoctor(MyDoctor myDoctor, @Nullable Consumer<Void> onSuccess,
                        @Nullable Consumer<Throwable> onError);

    void deleteMyDoctor(MyDoctor myDoctor, @Nullable Consumer<Void> onSuccess,
                        @Nullable Consumer<Throwable> onError);
}
