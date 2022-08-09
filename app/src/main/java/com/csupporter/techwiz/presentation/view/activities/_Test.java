package com.csupporter.techwiz.presentation.view.activities;

import androidx.core.util.Consumer;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class _Test {

    public static void getData(String path, Consumer<QuerySnapshot> onComplete, Consumer<Throwable> onError) {
        FirebaseFirestore.getInstance().collection(path).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        complete(onComplete, task.getResult());
                    else error(onError, task.getException());
                }).addOnFailureListener(e -> error(onError, e));
    }

    private static void complete(Consumer<QuerySnapshot> onComplete, QuerySnapshot data) {
        if (onComplete != null) {
            onComplete.accept(data);
        }
    }

    private static void error(Consumer<Throwable> onError, Throwable error) {
        if (onError != null) {
            onError.accept(error);
        }
    }
}
