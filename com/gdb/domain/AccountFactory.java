package com.gdb.domain;

public final class AccountFactory {
    private static final int DEFAULT_FIXED_DEPOSIT_TERM_MONTHS = 12;
    private static final double DEFAULT_FIXED_DEPOSIT_RATE = 6.5;
    private static final String DEFAULT_EMPLOYER_NAME = "Unknown";

    private AccountFactory() {
    }

    public static IAccount createAccount(String type, String accountNumber, String name,
                                         int age, double balance, String status, String pin) {
        if (type == null) {
            throw new IllegalArgumentException("Account type cannot be null");
        }

        switch (type.trim().toUpperCase()) {
            case "SAVINGS":
                return new SavingsAccount(accountNumber, name, age, balance, status, pin, 500.0, 4.0);
            case "CURRENT":
                return new CurrentAccount(accountNumber, name, age, balance, status, pin, 5000.0);
            case "FIXED_DEPOSIT":
                return new FixedDepositAccount(accountNumber, name, age, balance, status, pin,
                        DEFAULT_FIXED_DEPOSIT_TERM_MONTHS, DEFAULT_FIXED_DEPOSIT_RATE);
            case "SALARY":
                return new SalaryAccount(accountNumber, name, age, balance, status, pin,
                        DEFAULT_EMPLOYER_NAME);
            default:
                throw new IllegalArgumentException("Unsupported account type: " + type);
        }
    }
}