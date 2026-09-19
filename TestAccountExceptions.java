import java.util.Locale;

import exception.AccountException;
import exception.InactiveAccountException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;
import exception.InvalidPinException;
import exception.MinimumBalanceViolationException;

public class TestAccountExceptions {
	public static void main(String[] args) {
		System.out.println("============================================================");
		System.out.println("ACCOUNT TEST WITH EXCEPTIONS");
		System.out.println("============================================================");
		System.out.println();

		Account john = null;
		Account alice = null;
		Account charlie = null;
		Account diana = null;
		Account eve = null;
		Account frank = null;

		System.out.println(">>> Test 1: Valid Account Creation");
		try {
			john = createAccount(1001, "John Doe", 25, 1000, "Savings");
			System.out.println("SUCCESS: " + describeAccount(john));
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 2: Invalid Age (under 18)");
		try {
			createAccount(1002, "Teen User", 16, 1000, "Savings");
			System.out.println("SUCCESS: Account created");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 3: Invalid Account Type");
		try {
			createAccount(1003, "Invalid Type", 22, 1000, "Invalid");
			System.out.println("SUCCESS: Account created");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 4: Minimum Balance on Creation");
		System.out.println();
		System.out.println("Creating Savings account with ₹300");
		try {
			createAccount(1004, "Low Balance", 24, 300, "Savings");
			System.out.println("SUCCESS: Account created");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 5: Valid Deposit and Withdrawal");
		try {
			alice = createAccount(1005, "Alice Brown", 30, 1000, "Current");
			System.out.println("Account: " + describeAccount(alice));
			System.out.println("Setting PIN 1234: " + (alice.setPin(1234) ? "SUCCESS" : "FAILED"));
			deposit(alice, 500);
			System.out.println("Depositing ₹500.0: SUCCESS");
			System.out.println("Balance after deposit: " + money(alice.getBalance()));
			withdraw(alice, 200, 1234);
			System.out.println("Withdrawing ₹200.0: SUCCESS");
			System.out.println("Balance after withdrawal: " + money(alice.getBalance()));
			System.out.println(describeAccount(alice));
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 6: Invalid Deposit (Negative Amount)");
		try {
			System.out.println("Attempting to deposit ₹-100.0");
			deposit(alice, -100);
			System.out.println("SUCCESS: Deposit accepted");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 7: Insufficient Balance");
		try {
			charlie = createAccount(1006, "Charlie Green", 35, 500, "Savings");
			charlie.setPin(1234);
			System.out.println("Account: " + describeAccount(charlie));
			System.out.println("Attempting to withdraw ₹1000.0");
			withdraw(charlie, 1000, 1234);
			System.out.println("SUCCESS: Withdrawal accepted");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 8: Minimum Balance Violation");
		try {
			diana = createAccount(1007, "Diana Prince", 28, 1000, "Savings");
			diana.setPin(1234);
			System.out.println("Account: " + describeAccount(diana));
			System.out.println("Attempting to withdraw ₹600.0");
			withdraw(diana, 600, 1234);
			System.out.println("SUCCESS: Withdrawal accepted");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 9: Inactive Account Operations");
		try {
			eve = createAccount(1008, "Eve Wilson", 32, 2000, "Current");
			System.out.println("Account: " + describeAccount(eve));
			System.out.println("Closing account: " + (eve.closeAccount() ? "SUCCESS" : "FAILED"));
			System.out.println("Attempting to deposit ₹100.0 on closed account");
			deposit(eve, 100);
			System.out.println("SUCCESS: Deposit accepted");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		try {
			System.out.println("Reopening account: " + (eve.reopenAccount() ? "SUCCESS" : "FAILED"));
			System.out.println("Depositing ₹100.0 after reopen: " + (deposit(eve, 100) ? "SUCCESS" : "FAILED"));
			System.out.println("Balance after deposit: " + money(eve.getBalance()));
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 10: PIN Verification");
		try {
			frank = createAccount(1009, "Frank Miller", 40, 1500, "Savings");
			System.out.println("Account: " + describeAccount(frank));
			System.out.println("Setting PIN 1234: " + (frank.setPin(1234) ? "SUCCESS" : "FAILED"));
			System.out.println("Withdrawing ₹200.0 with correct PIN: " + (withdraw(frank, 200, 1234) ? "SUCCESS" : "FAILED"));
			System.out.println();
			System.out.println("Balance: " + money(frank.getBalance()));
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		try {
			System.out.println("Attempting to withdraw ₹100.0 with incorrect PIN (9999)");
			withdraw(frank, 100, 9999);
			System.out.println("SUCCESS: Withdrawal accepted");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		Account noPinAccount = null;
		try {
			noPinAccount = createAccount(1010, "No Pin User", 29, 1000, "Current");
			System.out.println("Attempting to withdraw ₹100.0 without PIN set");
			withdraw(noPinAccount, 100, 1234);
			System.out.println("SUCCESS: Withdrawal accepted");
		} catch (AccountException exception) {
			System.out.println("EXCEPTION: " + exception.getMessage());
		}

		System.out.println(">>> Test 11: All Accounts Summary");
		System.out.println(describeAccount(john));
		System.out.println(describeAccount(alice));
		System.out.println(describeAccount(charlie));
		System.out.println(describeAccount(diana));
		System.out.println(describeAccount(eve));
		System.out.println(describeAccount(frank));
		System.out.println("============================================================");
		System.out.println("TEST COMPLETED!");
		System.out.println("============================================================");
	}

	private static Account createAccount(int accountNumber, String name, int age, int initialBalance, String accountType)
			throws AccountException {
		if (age < 18) {
			throw new AccountException("Customer must be at least 18 years old. Provided: " + age);
		}

		if (!"Savings".equals(accountType) && !"Current".equals(accountType)) {
			throw new AccountException("Account type must be 'Savings' or 'Current'. Provided: " + accountType);
		}

		if ("Savings".equals(accountType) && initialBalance < 500) {
			throw new MinimumBalanceViolationException(
					"Savings account requires minimum balance of ₹500.0. Provided: " + money(initialBalance));
		}

		if ("Current".equals(accountType) && initialBalance < 1000) {
			throw new MinimumBalanceViolationException(
					"Current account requires minimum balance of ₹1000.0. Provided: " + money(initialBalance));
		}

		return new Account(accountNumber, name, age, initialBalance, accountType);
	}

	private static boolean deposit(Account account, double amount) throws AccountException {
		if (account == null) {
			throw new AccountException("Account not initialized");
		}

		if ("Inactive".equals(account.getStatus())) {
			throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
		}

		if (amount <= 0) {
			throw new InvalidAmountException("Deposit amount must be positive. Provided: " + money(amount));
		}

		return account.deposit(amount);
	}

	private static boolean withdraw(Account account, double amount, int pin) throws AccountException {
		if (account == null) {
			throw new AccountException("Account not initialized");
		}

		if ("Inactive".equals(account.getStatus())) {
			throw new InactiveAccountException("Account is inactive. Please reopen the account or contact support.");
		}

		if (amount <= 0) {
			throw new InvalidAmountException("Withdrawal amount must be positive. Provided: " + money(amount));
		}

		if (!account.hasPin()) {
			throw new InvalidPinException("PIN not set for this account");
		}

		if (!account.verifyPin(pin)) {
			throw new InvalidPinException("Incorrect PIN");
		}

		if (account.getBalance() < amount) {
			throw new InsufficientBalanceException(
					"Insufficient balance. Available: " + money(account.getBalance()) + ", Requested: " + money(amount));
		}

		double remainingBalance = account.getBalance() - amount;
		double minimumBalance = "Savings".equals(account.getAccountType()) ? 500.0 : 1000.0;
		if (remainingBalance < minimumBalance) {
			throw new MinimumBalanceViolationException(
					"Cannot withdraw. Minimum balance of " + money(minimumBalance)
							+ " required. Available after withdrawal: " + money(remainingBalance));
		}

		return account.withdraw(amount, pin);
	}

	private static String describeAccount(Account account) {
		if (account == null) {
			return "Account unavailable";
		}

		return "Account #" + account.getAccountNumber() + " | " + account.getName() + " (" + account.getAge()
				+ " yrs) | " + account.getAccountType() + " | " + money(account.getBalance()) + " | "
				+ account.getStatus() + " | PIN: " + (account.hasPin() ? "Yes" : "No");
	}

	private static String money(double amount) {
		return String.format(Locale.US, "₹%.1f", amount);
	}
}
