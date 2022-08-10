package com.csupporter.techwiz.data.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.data.firebase_source.FirebaseUtils;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.repository.AccountRepository;

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
    public void checkUserNameExits(String text, @Nullable Consumer<Boolean> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.checkFieldExits(DEFAULT_PATH, "userName", text, onSuccess, onError);
    }

    @Override
    public void checkEmailExits(String text, @Nullable Consumer<Boolean> onSuccess, @Nullable Consumer<Throwable> onError) {
        FirebaseUtils.checkFieldExits(DEFAULT_PATH, "email", text, onSuccess, onError);
    }

}
