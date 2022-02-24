package com.accounts;

import com.accounts.models.Account;

import java.util.Date;

public class DataAccount {

    public static Account getList(){
        Account acc = new Account();
        acc.setId("12233d");
        acc.setNumber("19123412345678");
        acc.setLineAvailable(1110.0);
        acc.setLineUsed(1100.0);
        acc.setBalancePast(10.0);
        acc.setAmount(0.0);
        acc.setCreateDate(new Date(2022-02-16));
        acc.setIdClient("98765a");
        acc.setIdProducts("12345a");
        return acc;
    }

    public static Account saveDepWit(){
        Account acc = new Account();
        acc.setId("12233d");
        acc.setNumber("1234455667788");
        acc.setLineUsed(1100.0);
        acc.setAmount(10.0);
        acc.setCreateDate(new Date(2022-02-16));
        return acc;
    }
}
