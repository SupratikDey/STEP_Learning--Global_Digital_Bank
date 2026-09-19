package com.gdb.domain;

import com.gdb.exceptions.*;

public abstract class AbstractAccount extends Account implements IAccount {
    private final String accountId;
    private final String accountName;
    private final int accountAge;
    private final String accountType;
    private double accountBalance;
    private String accountStatus;
    private String accountPin;

    protected AbstractAccount(String accountId, String name, int age, double initialBalance,
                              String accountType, String status, String pin) throws IllegalArgumentException {
        super(parseAccountNumber(accountId), name, age, initialBalance);
        this.accountId = accountId;
        this.accountName = name;
        this.accountAge = age;
        this.accountType = accountType;
        this.accountBalance = initialBalance;
        this.accountStatus = status;
        this.accountPin = pin;
    }

    public final void withdraw(double amount, String pin) throws AccountException {
        validateActiveAccount();
        validatePinValue(pin);
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero");
        }
        processDebit(amount);
    }

    @Override
    public final void withdraw(double amount, int pin)
            throws InvalidAmountException, InsufficientBalanceException,
            MinimumBalanceViolationException, InactiveAccountException, InvalidPinException {
        try {
            withdraw(amount, String.valueOf(pin));
        } catch (InvalidAmountException | InsufficientBalanceException
                 | MinimumBalanceViolationException | InactiveAccountException
                 | InvalidPinException e) {
            throw e;
        } catch (AccountException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }
    }

    @Override
    public void deposit(double amount)
            throws InvalidAmountException, InactiveAccountException {
        validateActiveAccount();
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero");
        }
        accountBalance += amount;
    }

    protected abstract void processDebit(double amount) throws AccountException;

    protected final void setBalance(double balance) {
        accountBalance = balance;
    }

    protected final void validateActiveAccount() throws InactiveAccountException {
        if (!"ACTIVE".equalsIgnoreCase(accountStatus)) {
            throw new InactiveAccountException("Account is inactive");
        }
    }

    private void validatePinValue(String pin) throws InvalidPinException {
        if (accountPin == null) {
            throw new InvalidPinException("PIN is not set");
        }
        if (!accountPin.equals(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    @Override
    public String getName() {
        return accountName;
    }

    @Override
    public int getAge() {
        return accountAge;
    }

    @Override
    public double getBalance() {
        return accountBalance;
    }

    @Override
    public String getStatus() {
        return accountStatus;
    }

    @Override
    public Integer getPin() {
        return accountPin == null ? null : Integer.valueOf(accountPin);
    }

    @Override
    public boolean hasPin() {
        return accountPin != null;
    }

    @Override
    public void setPin(int pin) throws IllegalArgumentException {
        if (pin < 1000 || pin > 9999) {
            throw new IllegalArgumentException("PIN must be a 4-digit number");
        }
        accountPin = String.valueOf(pin);
    }

    @Override
    public void closeAccount() throws IllegalStateException {
        if (!"ACTIVE".equalsIgnoreCase(accountStatus)) {
            throw new IllegalStateException("Account is already closed");
        }
        accountStatus = "INACTIVE";
    }

    @Override
    public void reopenAccount() throws IllegalStateException {
        if ("ACTIVE".equalsIgnoreCase(accountStatus)) {
            throw new IllegalStateException("Account is already active");
        }
        accountStatus = "ACTIVE";
    }

    private static int parseAccountNumber(String accountId) {
        try {
            return Integer.parseInt(accountId.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            return Math.abs(accountId.hashCode());
        }
    }
}