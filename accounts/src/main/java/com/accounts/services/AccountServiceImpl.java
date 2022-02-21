package com.accounts.services;

import com.accounts.models.Account;
import com.accounts.repository.AccountDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements  IAccountService{

    private final AccountDao accountDao;

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

    @Override
    public Mono<Account> findByNumber(String number) {
        return accountDao.findByNumber(number);
    }

    @Override
    public Mono<Account> findByIdDepositAccount(String id, Double amount) {
        return accountDao.findById(id)
                .flatMap(a -> {
                    var getLineUsed = a.getLineUsed();
                    var updLineUsed = getLineUsed + amount;
                    a.setLineUsed(updLineUsed);
                    return accountDao.save(a);
                });
    }

    @Override
    public Mono<Account> findByIdWithdrawalAccount(String id, Double amount) {
        return accountDao.findById(id)
                .flatMap(a -> {
                    var getLineUsed = a.getLineUsed();
                    var updLineUsed = getLineUsed - amount;
                    if(updLineUsed < 0) return Mono.error(new Exception("Insufficient balance"));
                    a.setLineUsed(updLineUsed);
                    return accountDao.save(a);
                });
    }
}
