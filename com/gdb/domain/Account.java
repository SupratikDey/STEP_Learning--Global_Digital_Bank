package com.gdb.domain;

import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.exceptions.InvalidAmountException;
import com.gdb.exceptions.InvalidPinException;
import com.gdb.exceptions.MinimumBalanceViolationException;

public abstract class Account {
	private static final int MIN_AGE = 18;
	private static final int MIN_PIN = 1000;
	private static final int MAX_PIN = 9999;

	private int accountNumber;
	private String name;
	private int age;
	private double balance;
	private String status;
	protected Integer pin;

	public abstract double getMinimumBalance();

	public abstract String getAccountType();

	public Account(int accountNumber, String name, int age, double initialBalance) {
		if (age < MIN_AGE) {
			throw new IllegalArgumentException(
					"Customer must be at least " + MIN_AGE + " years old. Provided: " + age);
		}
		double minimumBalance = getMinimumBalance();
		if (initialBalance < minimumBalance) {
			throw new IllegalArgumentException(
					getAccountType() + " account requires minimum balance of ₹" + minimumBalance + ". Provided: ₹" + initialBalance);
		}
		this.accountNumber = accountNumber;
		this.name = name;
		this.age = age;
		this.balance = initialBalance;
		this.status = "Active";
		this.pin = null;
	}

	public void deposit(double amount) throws InvalidAmountException, InactiveAccountException {
		validateActive();
		validateAmount(amount);
		balance += amount;
	}

	public void withdraw(double amount, int pin)
			throws InvalidAmountException, InsufficientBalanceException, MinimumBalanceViolationException,
			InactiveAccountException, InvalidPinException {
		validateActive();
		validateAmount(amount);
		validatePin(pin);
		if (balance < amount) {
			throw new InsufficientBalanceException(
					"Insufficient balance. Available: ₹" + balance + ", Requested: ₹" + amount);
		}
		double remainingBalance = balance - amount;
		if (remainingBalance < getMinimumBalance()) {
			throw new MinimumBalanceViolationException(
					"Cannot withdraw. Minimum balance of ₹" + getMinimumBalance() + " required. Available after withdrawal: ₹" + remainingBalance);
		}
		balance = remainingBalance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getBalance() {
		return balance;
	}

	public String getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean closeAccount() {
		if ("Inactive".equals(status)) {
			return false;
		}
		status = "Inactive";
		return true;
	}

	public boolean reopenAccount() {
		if ("Active".equals(status)) {
			return false;
		}
		status = "Active";
		return true;
	}

	public boolean setPin(Integer pin) {
		if (pin == null || pin < MIN_PIN || pin > MAX_PIN) {
			return false;
		}
		this.pin = pin;
		return true;
	}

	public boolean hasPin() {
		return pin != null;
	}

	public boolean verifyPin(int pin) {
		return this.pin != null && this.pin == pin;
	}

	protected void validateActive() throws InactiveAccountException {
		if (!"Active".equals(status)) {
			throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
		}
	}

	protected void validateAmount(double amount) throws InvalidAmountException {
		if (amount <= 0) {
			throw new InvalidAmountException("Amount must be positive. Provided: ₹" + amount);
		}
	}

	protected void validatePin(int pin) throws InvalidPinException {
		if (!hasPin()) {
			throw new InvalidPinException("PIN not set for this account");
		}
		if (!verifyPin(pin)) {
			throw new InvalidPinException("Incorrect PIN");
		}
	}

	protected void setBalance(double balance) {
		this.balance = balance;
	}
}