package com.gdb.domain;
import GDB.exceptions.*;

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
// ===== Abstract Methods =====
public abstract double getMinimumBalance();
public abstract String getAccountType();
// ===== Constructor =====
public Account(int accountNumber, String name, int age,
double initialBalance)
throws IllegalArgumentException {
// Validate age
if (age < MIN_AGE) {
throw new IllegalArgumentException(
"Customer must be at least " + MIN_AGE + " years old. Provided: " + age
);
}

// Validate minimum balance (delegated to subclass)
double minBalance = getMinimumBalance();
if (initialBalance < minBalance) {
throw new IllegalArgumentException(
getAccountType() + " account requires minimum balance of ₹" + minBalance +
". Provided: ₹" + initialBalance
);
}
// Initialize fields
this.accountNumber = accountNumber;
this.name = name;
this.age = age;
this.balance = initialBalance;
this.status = "Active";
this.pin = null;
}
// ===== Other Methods (Same as before) =====
// ... deposit, withdraw, closeAccount, reopenAccount, etc.
// ... all getters and setters
}