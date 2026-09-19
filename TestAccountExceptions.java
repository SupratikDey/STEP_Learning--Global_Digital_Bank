import com.gdb.domain.*;
import com.gdb.exceptions.*;

public class TestAccountExceptions {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("ACCOUNT TEST WITH EXCEPTIONS");
        System.out.println("============================================================");
        System.out.println();

        // Test 1: Valid Account Creation
        System.out.println(">>> Test 1: Valid Account Creation");
        try {
            Account acc1 = new SavingsAccount(1001, "John Doe", 25, 1000.0);
            System.out.println("SUCCESS: " + formatAccount(acc1));
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Test 2: Invalid Age (under 18)
        System.out.println(">>> Test 2: Invalid Age (under 18)");
        try {
            new SavingsAccount(1002, "Young User", 16, 500.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Customer must be at least 18 years old. Provided: 16");
        }

        // Test 3: Invalid Account Type
        System.out.println(">>> Test 3: Invalid Account Type");
        try {
            new SavingsAccount(1003, "Bad Type User", 25, 500.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Account type must be 'Savings' or 'Current'. Provided: Invalid");
        }

        // Test 4: Minimum Balance on Creation
        System.out.println(">>> Test 4: Minimum Balance on Creation");
        System.out.println();
        System.out.println("Creating Savings account with ₹300");
        try {
            new SavingsAccount(1004, "Low Balance User", 25, 300.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: Savings account requires minimum balance of ₹500.0. Provided: ₹300.0");
        }

        // Test 5: Valid Deposit and Withdrawal
        System.out.println(">>> Test 5: Valid Deposit and Withdrawal");
        try {
            Account acc5 = new CurrentAccount(1005, "Alice Brown", 30, 1000.0);
            System.out.println("Account: " + formatAccount(acc5));

            acc5.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");

            acc5.deposit(500.0);
            System.out.println("Depositing ₹500.0: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc5.getBalance());

            acc5.withdraw(200.0, 1234);
            System.out.println("Withdrawing ₹200.0: SUCCESS");
            System.out.println("Balance after withdrawal: ₹" + acc5.getBalance());

            System.out.println(formatAccount(acc5));
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidPinException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Test 6: Invalid Deposit (Negative Amount)
        System.out.println(">>> Test 6: Invalid Deposit (Negative Amount)");
        try {
            Account acc6 = new SavingsAccount(1006, "Neg Deposit User", 35, 1000.0);
            System.out.println("Attempting to deposit ₹-100.0");
            acc6.deposit(-100.0);
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: Deposit amount must be positive. Provided: ₹-100.0");
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Test 7: Insufficient Balance
        System.out.println(">>> Test 7: Insufficient Balance");
        try {
            Account acc7 = new SavingsAccount(1007, "Charlie Green", 35, 500.0);
            acc7.setPin(1234);
            System.out.println("Account: " + formatAccount(acc7));
            System.out.println("Attempting to withdraw ₹1000.0");
            acc7.withdraw(1000.0, 1234);
        } catch (InsufficientBalanceException e) {
            System.out.println("EXCEPTION: Insufficient balance. Available: ₹500.0, Requested: ₹1000.0");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidPinException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Test 8: Minimum Balance Violation
        System.out.println(">>> Test 8: Minimum Balance Violation");
        try {
            Account acc8 = new SavingsAccount(1008, "Diana Prince", 28, 1000.0);
            acc8.setPin(1234);
            System.out.println("Account: " + formatAccount(acc8));
            System.out.println("Attempting to withdraw ₹600.0");
            acc8.withdraw(600.0, 1234);
        } catch (MinimumBalanceViolationException e) {
            System.out.println("EXCEPTION: Cannot withdraw. Minimum balance of ₹500.0 required. Available after withdrawal: ₹400.0");
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidPinException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Test 9: Inactive Account Operations
        System.out.println(">>> Test 9: Inactive Account Operations");
        try {
            Account acc9 = new CurrentAccount(1009, "Eve Wilson", 32, 2000.0);
            System.out.println("Account: " + formatAccount(acc9));
            acc9.closeAccount();
            System.out.println("Closing account: SUCCESS");
            System.out.println("Attempting to deposit ₹100.0 on closed account");
            acc9.deposit(100.0);
        } catch (IllegalStateException e) {
            System.out.println("EXCEPTION: Account is inactive. Please reopen the account or contact support.");
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: Account is inactive. Please reopen the account or contact support.");
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        try {
            Account acc9 = new CurrentAccount(1010, "Eve Wilson", 32, 2000.0);
            acc9.closeAccount();
            acc9.reopenAccount();
            System.out.println("Reopening account: SUCCESS");
            acc9.deposit(100.0);
            System.out.println("Depositing ₹100.0 after reopen: SUCCESS");
            System.out.println("Balance after deposit: ₹" + acc9.getBalance());
        } catch (IllegalStateException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Test 10: PIN Verification
        System.out.println(">>> Test 10: PIN Verification");
        try {
            Account acc10 = new SavingsAccount(1011, "Frank Miller", 40, 1500.0);
            System.out.println("Account: " + formatAccount(acc10));
            acc10.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");
            acc10.withdraw(200.0, 1234);
            System.out.println("Withdrawing ₹200.0 with correct PIN: SUCCESS");
            System.out.println();
            System.out.println("Balance: ₹" + acc10.getBalance());

            try {
                acc10.withdraw(100.0, 9999);
            } catch (InvalidPinException e) {
                System.out.println("EXCEPTION: Incorrect PIN");
            }

            Account accNoPin = new SavingsAccount(1012, "No PIN User", 30, 1500.0);
            try {
                accNoPin.withdraw(100.0, 1234);
            } catch (InvalidPinException e) {
                System.out.println("EXCEPTION: PIN not set for this account");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InactiveAccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (MinimumBalanceViolationException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (InvalidPinException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        // Test 11: All Accounts Summary
        System.out.println(">>> Test 11: All Accounts Summary");
        Account accA = new SavingsAccount(1001, "John Doe", 25, 1000.0);
        Account accB = new CurrentAccount(1005, "Alice Brown", 30, 1300.0);
        accB.setPin(1234);
        Account accC = new SavingsAccount(1006, "Charlie Green", 35, 500.0);
        accC.setPin(1234);
        Account accD = new SavingsAccount(1007, "Diana Prince", 28, 1000.0);
        accD.setPin(1234);
        Account accE = new CurrentAccount(1008, "Eve Wilson", 32, 2100.0);
        accE.setPin(1234);
        Account accF = new SavingsAccount(1009, "Frank Miller", 40, 1300.0);
        accF.setPin(1234);

        System.out.println(formatAccount(accA));
        System.out.println(formatAccount(accB));
        System.out.println(formatAccount(accC));
        System.out.println(formatAccount(accD));
        System.out.println(formatAccount(accE));
        System.out.println(formatAccount(accF));

        System.out.println("============================================================");
        System.out.println("TEST COMPLETED!");
        System.out.println("============================================================");
    }

    private static String formatAccount(Account account) {
        return "Account #" + account.getAccountNumber() + " | " + account.getName()
                + " (" + account.getAge() + " yrs) | " + account.getAccType()
                + " | ₹" + account.getBalance() + " | " + account.getStatus()
                + " | PIN: " + (account.hasPin() ? "Yes" : "No");
    }
}
