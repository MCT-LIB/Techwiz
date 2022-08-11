package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.repository.AccountRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private final static String DEFAULT_PATH = "accounts";

    @Override
    public void addAccount(Account account, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, account.getId(), account, onSuccess, onError);
    }

    @Override
    public void updateAccount(Account account, @Nullable Consumer<Void> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.setData(DEFAULT_PATH, account.getId(), account, onSuccess, onError);
    }

    @Override
    public void checkUserNameAndPassword(String email, String password, @Nullable Consumer<Account> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH)
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        FirebaseUtils.error(onError, error.getCause());
                        return;
                    }
                    if (value == null) {
                        FirebaseUtils.error(onError, null);
                    } else {
                        if (value.getDocuments().isEmpty()) {
                            FirebaseUtils.success(onSuccess, null);
                        } else {
                            DocumentSnapshot snapshot = value.getDocuments().get(0);
                            Account account = snapshot.toObject(Account.class);
                            if (account != null) {
                                account.setId(snapshot.getId());
                                FirebaseUtils.success(onSuccess, account);
                            }
                        }
                    }
                });
    }

    @Override
    public void findAccountByEmail(String email, @Nullable Consumer<Account> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH).whereEqualTo("email", email).addSnapshotListener((value, error) -> {
            if (value != null) {
                if (!value.isEmpty()) {
                    DocumentSnapshot snapshot = value.getDocuments().get(0);
                    Account acc = snapshot.toObject(Account.class);
                    if (acc != null) {
                        acc.setId(snapshot.getId());
                        FirebaseUtils.success(onSuccess, acc);
                    } else {
                        FirebaseUtils.success(onSuccess, null);
                    }
                } else {
                    FirebaseUtils.success(onSuccess, null);
                }
            } else {
                FirebaseUtils.error(onError, error != null ? error.getCause() : null);
            }
        });
    }

    @Override
    public void findAccountById(String id, @Nullable Consumer<Account> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH).document(id).addSnapshotListener((value, error) -> {
            if (value != null) {
                Account acc = value.toObject(Account.class);
                if (acc != null) {
                    acc.setId(value.getId());
                    FirebaseUtils.success(onSuccess, acc);
                } else {
                    FirebaseUtils.success(onSuccess, null);
                }
            } else {
                FirebaseUtils.error(onError, error != null ? error.getCause() : null);
            }
        });
    }

    @Override
    public void getAllDoctor(@Nullable Consumer<List<Account>> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.db().collection(DEFAULT_PATH).whereEqualTo("type", Account.TYPE_DOCTOR)
                .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        List<Account> accounts = new ArrayList<>();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Account account = document.toObject(Account.class);
                            if (account != null) {
                                account.setId(document.getId());
                                accounts.add(account);
                            }
                        }
                        FirebaseUtils.success(onSuccess, accounts);
                    } else {
                        FirebaseUtils.error(onError, error != null ? error.getCause() : null);
                    }
                });
    }

}
