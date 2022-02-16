package com.accounts.services;

import com.accounts.models.Account;
import com.accounts.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements  IAccountService{

    @Autowired
    private AccountDao accountDao;



    @Override
    public Flux<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Mono<Account> save(Account account) {
        return accountDao.save(account);
    }

    @Override
    public Mono<Void> delete(Account account) {
        return accountDao.delete(account);
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountDao.findById(id);
    }
}
