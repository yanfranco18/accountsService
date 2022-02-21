package com.accounts.repository;

import com.accounts.models.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountDao extends ReactiveMongoRepository<Account, String> {

    public Mono<Account> findByNumber(String number);

}
