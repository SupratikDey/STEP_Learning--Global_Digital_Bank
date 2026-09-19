package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InsufficientBalanceException;

public class SalaryAccount extends AbstractAccount {
    private int inactiveMonths;
    private final String employerName;

    public SalaryAccount(String accountNumber, String name, int age, double balance,
                         String status, String pin, String employerName) {
        super(accountNumber, name, age, balance, "SALARY", status, pin);
        this.employerName = employerName;
        this.inactiveMonths = 0;
    }

    @Override
    protected void processDebit(double amount) throws AccountException {
        if (amount > getBalance()) {
            throw new InsufficientBalanceException("Insufficient funds in Salary account");
        }
        setBalance(getBalance() - amount);
    }

    @Override
    public double getMinimumBalance() {
        return 0.0;
    }

    public String getEmployerName() {
        return employerName;
    }

    public int getInactiveMonths() {
        return inactiveMonths;
    }

    public void incrementInactiveMonths() {
        inactiveMonths++;
    }
}