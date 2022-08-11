package com.csupporter.techwiz.domain.repository;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import com.csupporter.techwiz.domain.model.Account;


public interface AccountRepository {

    void checkUserNameAndPassword(String email, String password,
                                  @Nullable Consumer<Account> onSuccess,
                                  @Nullable Consumer<Throwable> onError);

    void addAccount(Account account,
                    @Nullable Consumer<Void> onSuccess,
                    @Nullable Consumer<Throwable> onError);

    void updateAccount(Account account,
                       @Nullable Consumer<Void> onSuccess,
                       @Nullable Consumer<Throwable> onError);

    void findAccountByEmail(String text,
                            @Nullable Consumer<Account> onSuccess,
                            @Nullable Consumer<Throwable> onError);

}
