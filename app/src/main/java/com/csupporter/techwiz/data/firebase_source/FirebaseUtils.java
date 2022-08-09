package com.csupporter.techwiz.data.firebase_source;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class FirebaseUtils {

    @NonNull
    public static String uniqueId() {
        return UUID.randomUUID().toString();
    }

    /**
     * @param id        id === path
     * @param uri       image uri
     * @param onSuccess listener
     * @param onError   listener
     */
    public static void uploadImage(@NonNull String id, @NonNull Uri uri,
                                   @Nullable Consumer<Uri> onSuccess,
                                   @Nullable Consumer<Throwable> onError) {
        StorageReference ref = FirebaseStorage.getInstance().getReference(id);
        ref.putFile(uri).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                error(onError, task.getException());
            }
            // Continue with the task to get the download URL
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                success(onSuccess, task.getResult());
            } else {
                error(onError, task.getException());
            }
        }).addOnFailureListener(e -> error(onError, e));
    }

    /**
     * @param id id === path
     */
    public static void deleteImage(@NonNull String id) {
        FirebaseStorage.getInstance().getReference(id).delete();
    }

    /**
     * @param id id === path
     */
    public static void deleteImage(@NonNull String id,
                                   @Nullable Consumer<Void> onSuccess,
                                   @Nullable Consumer<Throwable> onError) {
        FirebaseStorage.getInstance().getReference(id).delete()
                .addOnSuccessListener(unused -> success(onSuccess, unused))
                .addOnFailureListener(e -> error(onError, e));
    }

    public static void getData(@NonNull String path,
                               @Nullable Consumer<QuerySnapshot> onSuccess,
                               @Nullable Consumer<Throwable> onError) {
        FirebaseFirestore.getInstance().collection(path).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        success(onSuccess, task.getResult());
                    else {
                        error(onError, task.getException());
                    }
                }).addOnFailureListener(e -> error(onError, e));
    }

    public static void setData(@NonNull String path, @NonNull String id, Object object,
                               @Nullable Consumer<Void> onSuccess,
                               @Nullable Consumer<Throwable> onError) {
        FirebaseFirestore.getInstance().collection(path).document(id).set(object)
                .addOnSuccessListener(unused -> success(onSuccess, unused))
                .addOnFailureListener(e -> error(onError, e));
    }

    public static void deleteData(@NonNull String path, @NonNull String id) {
        FirebaseFirestore.getInstance().collection(path).document(id).delete();
    }

    public static void deleteData(@NonNull String path, @NonNull String id,
                                  @Nullable Consumer<Void> onSuccess,
                                  @Nullable Consumer<Throwable> onError) {
        FirebaseFirestore.getInstance().collection(path).document(id).delete()
                .addOnSuccessListener(unused -> success(onSuccess, unused))
                .addOnFailureListener(e -> error(onError, e));
    }

    private static <T> void success(Consumer<T> consumer, T t) {
        if (consumer != null) {
            consumer.accept(t);
        }
    }

    private static void error(Consumer<Throwable> onError, Throwable error) {
        if (onError != null) {
            if (error == null)
                onError.accept(new RuntimeException("Error"));
            else onError.accept(error);
        }
    }
}
