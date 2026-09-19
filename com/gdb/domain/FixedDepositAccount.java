package com.gdb.domain;

import com.gdb.exceptions.AccountException;

public class FixedDepositAccount extends AbstractAccount {
    private final int termMonths;
    private final double interestRate;

    public FixedDepositAccount(String accountId, String name, int age, double initialBalance,
                               String status, String pin, int termMonths, double interestRate)
            throws IllegalArgumentException {
        super(accountId, name, age, initialBalance, "FIXED_DEPOSIT", status, pin);
        if (termMonths <= 0 || interestRate < 0) {
            throw new IllegalArgumentException("Fixed deposit terms are invalid");
        }
        this.termMonths = termMonths;
        this.interestRate = interestRate;
    }

    @Override
    public double getMinimumBalance() {
        return 0.0;
    }

    @Override
    public String getAccountType() {
        return "Fixed Deposit";
    }

    @Override
    protected void processDebit(double amount) throws AccountException {
        throw new AccountException("Fixed deposit cannot be withdrawn before maturity");
    }

    public int getTermMonths() {
        return termMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }
}