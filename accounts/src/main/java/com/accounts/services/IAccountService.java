package com.accounts.services;

import com.accounts.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountService {


    public Flux<Account> findAll();

    public Mono<Account> save(Account account);

    public Mono<Void> delete(String id);

    public Mono<Account> findById(String id);

    public Mono<Account> findByNumber(String number);

    //metodo para deposite
    public Mono<Account> saveDepositAccount(Account account);
    //metodo para retiro
    public Mono<Account> saveWithdrawalAccount(Account account);





}
