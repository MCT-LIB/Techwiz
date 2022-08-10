package com.csupporter.techwiz.data.repository;

import com.csupporter.techwiz.domain.repository.Repository;

public class RepositoryImpl extends Repository {

    public RepositoryImpl() {
        super(new AccountRepositoryImpl());
    }
}
