package com.accounts.services;

import com.accounts.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {


    public Flux<Account> findAll();

    public Mono<Account> save(Account account);

    public Mono<Void> delete(Account account);

    public Mono<Account> findById(String id);

    public Mono<Account> findByNumber(String number);

    //metodo para deposite
    public Mono<Account> findByIdDepositAccount(String id, Double amount);
    //metodo para retiro
    public Mono<Account> findByIdWithdrawalAccount(String id, Double amount);





}
