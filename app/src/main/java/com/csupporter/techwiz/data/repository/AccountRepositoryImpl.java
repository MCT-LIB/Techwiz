package com.csupporter.techwiz.data.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.repository.AccountRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class AccountRepositoryImpl implements AccountRepository {

    private final static String DEFAULT_PATH = "accounts";

    @Override
    public void checkUserNameAndPassword(String email, String password, @Nullable Consumer<Account> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH)
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            FirebaseUtils.error(onError, error.getCause());
                            return;
                        }
                        if (value == null) {
                            FirebaseUtils.error(onError, null);
                        } else {
                            if (value.getDocuments().isEmpty()) {
                                FirebaseUtils.error(onError, null);
                            } else {
                                DocumentSnapshot snapshot = value.getDocuments().get(0);
                                Account account = snapshot.toObject(Account.class);
                                if (account != null) {
                                    account.setId(snapshot.getId());
                                    FirebaseUtils.success(onSuccess, account);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void addAccount(Account account, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, account.getId(), account, onSuccess, onError);
    }

    @Override
    public void updateAccount(Account account, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, account.getId(), account, onSuccess, onError);
    }

    @Override
    public void checkUserNameExits(String text, @Nullable Consumer<Boolean> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.checkFieldExits(DEFAULT_PATH, "userName", text, onSuccess, onError);
    }

    @Override
    public void checkEmailExits(String text, @Nullable Consumer<Boolean> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.checkFieldExits(DEFAULT_PATH, "email", text, onSuccess, onError);
    }

}
