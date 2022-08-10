package com.csupporter.techwiz.domain.repository;

public abstract class Repository {

    public final AccountRepository account;

    public Repository(AccountRepository account) {
        this.account = account;
    }
}
