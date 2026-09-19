package com.gdb.domain;

import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.exceptions.InvalidAmountException;
import com.gdb.exceptions.InvalidPinException;
import com.gdb.exceptions.MinimumBalanceViolationException;

public class CurrentAccount extends Account {
    private static final double MINIMUM_BALANCE = 1000.0;
    private static final String ACCOUNT_TYPE = "Current";
    private static final double OVERDRAFT_LIMIT = 5000.0;

    private double overdraftUsed;

    public CurrentAccount(int accountNumber, String name, int age, double initialBalance) {
        super(accountNumber, name, age, initialBalance);
        overdraftUsed = 0.0;
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
    public void withdraw(double amount, int pin)
            throws InvalidAmountException, InsufficientBalanceException, MinimumBalanceViolationException,
            InactiveAccountException, InvalidPinException {
        validateActive();
        validateAmount(amount);
        validatePin(pin);

        double availableAmount = getBalance() + getAvailableOverdraft() - getMinimumBalance();
        if (amount > availableAmount) {
            throw new InsufficientBalanceException(
                    "Insufficient funds. Available: ₹" + availableAmount + " (including ₹" + OVERDRAFT_LIMIT + " overdraft), Requested: ₹" + amount);
        }

        double newBalance = getBalance() - amount;
        if (newBalance < getMinimumBalance()) {
            overdraftUsed = getMinimumBalance() - newBalance;
        }
        setBalance(newBalance);
    }

    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }

    public double getOverdraftUsed() {
        return overdraftUsed;
    }

    public double getAvailableOverdraft() {
        return OVERDRAFT_LIMIT - overdraftUsed;
    }

    public boolean isUsingOverdraft() {
        return overdraftUsed > 0;
    }

    public void repayOverdraft(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Repayment amount must be positive");
        }
        if (amount > overdraftUsed) {
            throw new IllegalArgumentException("Amount exceeds overdraft used (₹" + overdraftUsed + ")");
        }
        overdraftUsed -= amount;
        setBalance(getBalance() + amount);
    }
}