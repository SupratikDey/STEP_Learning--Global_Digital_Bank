package com.gdb.domain;

import com.gdb.exceptions.AccountException;

public interface IAccount {
    String getAccountType();
    String getName();
    double getBalance();
    String getStatus();
    double getMinimumBalance();
    void deposit(double amount) throws AccountException;
    void withdraw(double amount, String pin) throws AccountException;
}