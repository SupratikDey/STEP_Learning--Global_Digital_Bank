package com.gdb.domain;

import com.gdb.exceptions.*;

public class CurrentAccount extends AbstractAccount {
    private static final double MINIMUM_BALANCE = 1000.0;
    private static final String ACCOUNT_TYPE = "Current";
    private static final double DEFAULT_OVERDRAFT_LIMIT = 5000.0;
    private final double overdraftLimit;
    private double overdraftUsed;

    public CurrentAccount(int accountNumber, String name, int age, double initialBalance)
            throws IllegalArgumentException {
        this(String.valueOf(accountNumber), name, age, initialBalance, "ACTIVE", null, DEFAULT_OVERDRAFT_LIMIT);
    }

    public CurrentAccount(String accountNumber, String name, int age, double initialBalance,
                          String status, String pin, double overdraftLimit)
            throws IllegalArgumentException {
        super(accountNumber, name, age, initialBalance, ACCOUNT_TYPE, status, pin);
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit must be non-negative");
        }
        this.overdraftLimit = overdraftLimit;
        this.overdraftUsed = 0.0;
    }

    @Override
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    @Override
    public String getAccountType() {
        return ACCOUNT_TYPE;
    }

        @Override
        protected void processDebit(double amount) throws AccountException {
        double availableBalance = getBalance() + overdraftLimit - overdraftUsed;
        if (amount > availableBalance) {
            throw new InsufficientBalanceException(
                    "Insufficient funds. Available: ₹" + availableBalance
                    + " (including ₹" + overdraftLimit + " overdraft), Requested: ₹" + amount);
        }

        double newBalance = getBalance() - amount;
        if (newBalance < getMinimumBalance()) {
            overdraftUsed += getMinimumBalance() - newBalance;
        }
        setBalance(newBalance);
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getOverdraftUsed() {
        return overdraftUsed;
    }

    public double getAvailableOverdraft() {
        return overdraftLimit - overdraftUsed;
    }

    public boolean isUsingOverdraft() {
        return overdraftUsed > 0;
    }

    public void repayOverdraft(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Repayment amount must be positive");
        }
        if (amount > overdraftUsed) {
            throw new IllegalArgumentException(
                    "Amount exceeds overdraft used (₹" + overdraftUsed + ")");
        }
        overdraftUsed -= amount;
        setBalance(getBalance() + amount);
    }
}