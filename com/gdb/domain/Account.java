package com.gdb.domain;

import com.gdb.exceptions.*;

public abstract class Account {

    // ===== Constants =====
    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    // ===== Fields =====
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String status;
    private Integer pin;
    private double dailyWithdrawalTotal;

    public abstract double getMinimumBalance();

    public abstract String getAccountType();

    // ===== Constructor =====
    public Account(int accountNumber, String name, int age,
                   double initialBalance)
            throws IllegalArgumentException {

        if (age < MIN_AGE) {
                throw new IllegalArgumentException(
                    "Customer must be at least 18 years old. Provided: " + age);
        }

            double minimumBalance = getMinimumBalance();
            if (initialBalance < minimumBalance) {
            throw new IllegalArgumentException(
                    getAccountType() + " account requires minimum balance of ₹" + minimumBalance
                        + ". Provided: ₹" + initialBalance);
        }

        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.status = "Active";
        this.pin = null;
        this.dailyWithdrawalTotal = 0.0;
    }

    // ===== Business Methods =====
        public void deposit(double amount)
            throws InvalidAmountException, InactiveAccountException {

        validateActive();

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero"
            );
        }

        this.balance += amount;
    }

    public void withdraw(double amount, int pin)
            throws InvalidAmountException,
                   InsufficientBalanceException,
                   MinimumBalanceViolationException,
                   InactiveAccountException,
                   InvalidPinException {

        validateActive();

        validatePin(pin);
        validateAmount(amount);

        if (amount > this.balance) {
            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        if (this.balance - amount < getMinimumBalance()) {
            throw new MinimumBalanceViolationException(
                    "Minimum balance cannot be violated"
            );
        }

        this.balance -= amount;
        updateDailyWithdrawalTotal(amount);
    }

    public void closeAccount() throws IllegalStateException {
        if ("Inactive".equalsIgnoreCase(this.status)) {
            throw new IllegalStateException("Account is already closed");
        }

        this.status = "Inactive";
    }

    public void reopenAccount() throws IllegalStateException {
        if ("Active".equalsIgnoreCase(this.status)) {
            throw new IllegalStateException("Account is already active");
        }

        this.status = "Active";
    }

    public void setPin(int pin) throws IllegalArgumentException {
        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException("PIN must be a 4-digit number");
        }

        this.pin = pin;
    }

    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin.equals(pin);
    }

    public boolean hasPin() {
        return this.pin != null;
    }

    protected void validateActive() throws InactiveAccountException {
        if (!"Active".equalsIgnoreCase(this.status)) {
            throw new InactiveAccountException("Account is inactive");
        }
    }

    protected void validatePin(int pin) throws InvalidPinException {
        if (!hasPin()) {
            throw new InvalidPinException("PIN is not set");
        }
        if (!verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }
    }

    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    protected void updateDailyWithdrawalTotal(double amount) {
        this.dailyWithdrawalTotal += amount;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAccType() {
        return getAccountType();
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getPin() {
        return this.pin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Age must be 18 or above");
        }

        this.age = age;
    }

}