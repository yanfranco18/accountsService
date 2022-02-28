package com.accounts.services;

import com.accounts.clientWebClient.IClientWebClientService;
import com.accounts.models.Account;
import com.accounts.repository.AccountDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements  IAccountService{

    private final AccountDao accountDao;
    private final IClientWebClientService clientService;

    @Override
    public Flux<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Mono<Account> save(Account account) {
        return clientService.findById(account.getIdClient())
                        .flatMap(c -> {
                            Account acc = new Account();
                            acc.setNumber(account.getNumber());
                            acc.setLineAvailable(account.getLineAvailable());
                            acc.setLineUsed(account.getLineUsed());
                            acc.setBalancePast(account.getBalancePast());
                            acc.setAmount(account.getAmount());
                            acc.setCreateDate(new Date());
                            acc.setCountMovements(account.getCountMovements());
                            acc.setIdClient(c.getId());
                            acc.setIdProducts(account.getIdProducts());
                            return accountDao.save(acc);
                        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return accountDao.deleteById(id);
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
    public Mono<Account> saveDepositAccount(Account account){
        return accountDao.findById(account.getId())
                .flatMap(a -> {
                    var oldlineUsed = a.getLineUsed();
                    a.setLineUsed(oldlineUsed + account.getAmount());
                    a.setAmount(account.getAmount());
                    var countMov = a.getCountMovements()+0;
                    a.setCountMovements(countMov+1);
                    a.setCreateDate(new Date());
                    return accountDao.save(a);
                });
    }

    @Override
    public Mono<Account> saveWithdrawalAccount(Account account) {
        return accountDao.findById(account.getId())
                .flatMap(a -> {
                    var oldlineUsed = a.getLineUsed();
                    a.setLineUsed(oldlineUsed - account.getAmount());
                    a.setAmount(account.getAmount());
                    var countMov = a.getCountMovements()+0;
                    a.setCountMovements(countMov+1);
                    a.setCreateDate(new Date());
                    if(a.getLineUsed() < 0) return Mono.error(new Exception("Insufficient balance"));
                    return accountDao.save(a);
                });
    }
}
