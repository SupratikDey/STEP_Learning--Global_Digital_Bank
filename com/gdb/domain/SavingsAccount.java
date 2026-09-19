package com.gdb.domain;

import com.gdb.exceptions.*;

public class SavingsAccount extends AbstractAccount {
    private static final double MINIMUM_BALANCE = 500.0;
    private static final String ACCOUNT_TYPE = "Savings";
    private static final double INTEREST_RATE = 4.0;

    public SavingsAccount(int accountNumber, String name, int age, double initialBalance)
            throws IllegalArgumentException {
        this(String.valueOf(accountNumber), name, age, initialBalance, "ACTIVE", null, MINIMUM_BALANCE, INTEREST_RATE);
    }

    public SavingsAccount(String accountNumber, String name, int age, double initialBalance,
                          String status, String pin, double minimumBalance, double interestRate)
            throws IllegalArgumentException {
        super(accountNumber, name, age, initialBalance, ACCOUNT_TYPE, status, pin);
        if (minimumBalance != MINIMUM_BALANCE || interestRate != INTEREST_RATE) {
            throw new IllegalArgumentException("Savings account terms do not match configured product terms");
        }
    }

    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    @Override
    public String getAccountType() {
        return ACCOUNT_TYPE;
    }

    public double calculateInterest(int years) {
        if (years < 0) {
            throw new IllegalArgumentException("Years must be non-negative");
        }
        return getBalance() * (INTEREST_RATE / 100) * years;
    }

    public double getInterestRate() {
        return INTEREST_RATE;
    }

    @Override
    protected void processDebit(double amount) throws AccountException {
        if (getBalance() - amount < getMinimumBalance()) {
            throw new MinimumBalanceViolationException("Minimum balance cannot be violated");
        }
        setBalance(getBalance() - amount);
    }
}