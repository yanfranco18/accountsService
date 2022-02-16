package com.accounts.repository;

import com.accounts.models.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends ReactiveMongoRepository<Account, String> {
}
