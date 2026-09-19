import com.gdb.domain.Account;
import com.gdb.domain.CurrentAccount;
import com.gdb.domain.SavingsAccount;
import com.gdb.exceptions.AccountException;

public class TestAccountSubclasses {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("ACCOUNT SUBCLASSES TEST (SAVINGS & CURRENT)");
        System.out.println("============================================================");

        SavingsAccount savings = null;
        CurrentAccount current = null;

        System.out.println();
        System.out.println(">>> Test 1: Creating Accounts");
        try {
            savings = new SavingsAccount(1001, "John Doe", 25, 1000.0);
            current = new CurrentAccount(1002, "Jane Smith", 30, 2000.0);
            System.out.println("Savings Account: " + formatAccount(savings));
            System.out.println("Current Account: " + formatAccount(current));
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            finish();
            return;
        }

        System.out.println(">>> Test 2: Account Type and Minimum Balance");
        System.out.println("Savings Account - Type: " + savings.getAccountType()
                + ", Minimum Balance: ₹" + savings.getMinimumBalance());
        System.out.println("Current Account - Type: " + current.getAccountType()
                + ", Minimum Balance: ₹" + current.getMinimumBalance());

        System.out.println(">>> Test 3: Savings Account - Interest Calculation");
        System.out.println("Savings Account: " + formatAccountWithoutPin(savings));
        System.out.println();
        System.out.println("Interest Rate: " + savings.getInterestRate() + "% per annum");
        System.out.println("Interest for 1 year: ₹" + savings.calculateInterest(1));
        System.out.println("Interest for 2 years: ₹" + savings.calculateInterest(2));
        System.out.println("Interest for 5 years: ₹" + savings.calculateInterest(5));
        System.out.println("After 2 years with interest: Balance would be ₹"
                + (savings.getBalance() + savings.calculateInterest(2)));

        System.out.println(">>> Test 4: Current Account - Overdraft Feature");
        System.out.println("Current Account: " + formatAccountWithoutPin(current));
        System.out.println("Overdraft Limit: ₹" + current.getOverdraftLimit());
        System.out.println("Available Overdraft: ₹" + current.getAvailableOverdraft());
        System.out.println("Overdraft Used: ₹" + current.getOverdraftUsed());
        System.out.println("Is Using Overdraft: " + current.isUsingOverdraft());

        try {
            current.setPin(1234);
            System.out.println("Withdrawing ₹1500.0 (goes below minimum balance of ₹1000)");
            System.out.println("Balance before: ₹" + current.getBalance());
            current.withdraw(1500.0, 1234);
            System.out.println("Withdrawing: ₹1500.0 - SUCCESS");
            System.out.println("Balance after: ₹" + current.getBalance());
            System.out.println("Overdraft Used: ₹" + current.getOverdraftUsed());
            System.out.println("Available Overdraft: ₹" + current.getAvailableOverdraft());
            System.out.println("Is Using Overdraft: " + current.isUsingOverdraft());

            double availableFunds = current.getBalance() + current.getAvailableOverdraft();
            System.out.println("Attempting to withdraw ₹6000.0 (would exceed overdraft)");
            System.out.println("Available funds: ₹" + current.getBalance() + " (balance) + ₹"
                    + current.getAvailableOverdraft() + " (overdraft) = ₹" + availableFunds);
            current.withdraw(6000.0, 1234);
            System.out.println("Withdrawing ₹6000.0 - UNEXPECTED SUCCESS");
        } catch (AccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        try {
            System.out.println("Repaying overdraft of ₹" + current.getOverdraftUsed());
            System.out.println("Balance before repayment: ₹" + current.getBalance());
            System.out.println("Overdraft Used before: ₹" + current.getOverdraftUsed());
            current.repayOverdraft(current.getOverdraftUsed());
            System.out.println("Repaying overdraft - SUCCESS");
            System.out.println("Balance after repayment: ₹" + current.getBalance());
            System.out.println("Overdraft Used after: ₹" + current.getOverdraftUsed());
            System.out.println("Is Using Overdraft: " + current.isUsingOverdraft());
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println(">>> Test 5: Polymorphism - Treating Accounts Uniformly");
        Account[] accounts = {
                savings,
                current,
                new SavingsAccount(1003, "Bob Wilson", 35, 500.0),
                new CurrentAccount(1004, "Alice Brown", 28, 1500.0)
        };
        System.out.println("Processing accounts polymorphically:");
        double totalBalance = 0.0;
        for (Account account : accounts) {
            System.out.println(formatAccountWithType(account));
            totalBalance += account.getBalance();
        }
        System.out.println();
        System.out.println("Total accounts: " + accounts.length);
        System.out.println("Total balance across all accounts: ₹" + totalBalance);

        System.out.println(">>> Test 6: Validation - Invalid Creation Attempts");
        try {
            System.out.println("Attempting to create SavingsAccount with ₹300 (below minimum)");
            new SavingsAccount(2001, "Low Savings", 25, 300.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        try {
            System.out.println("Attempting to create CurrentAccount with ₹500 (below minimum)");
            new CurrentAccount(2002, "Low Current", 25, 500.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
        try {
            System.out.println("Attempting to create SavingsAccount with age 16");
            new SavingsAccount(2003, "Young Customer", 16, 500.0);
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println(">>> Test 7: Savings Account - PIN and Operations");
        try {
            SavingsAccount operationalSavings = new SavingsAccount(1005, "Charlie Green", 40, 2000.0);
            System.out.println("Savings Account: " + formatAccountWithoutPin(operationalSavings));
            operationalSavings.setPin(1234);
            System.out.println("Setting PIN 1234: SUCCESS");
            operationalSavings.deposit(500.0);
            System.out.println("Depositing ₹500.0: SUCCESS");
            System.out.println("Balance after deposit: ₹" + operationalSavings.getBalance());
            operationalSavings.withdraw(300.0, 1234);
            System.out.println("Withdrawing ₹300.0 with correct PIN: SUCCESS");
            System.out.println("Balance after withdrawal: ₹" + operationalSavings.getBalance());
            System.out.println("Attempting to withdraw ₹2000.0 (would violate minimum balance)");
            operationalSavings.withdraw(2000.0, 1234);
            System.out.println("Withdrawing ₹2000.0 - UNEXPECTED SUCCESS");
        } catch (AccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }

        System.out.println(">>> Test 8: Current Account - Active Status Operations");
        try {
            CurrentAccount operationalCurrent = new CurrentAccount(1006, "Diana Prince", 35, 3000.0);
            System.out.println("Current Account: " + formatAccountWithoutPin(operationalCurrent));
            operationalCurrent.closeAccount();
            System.out.println("Closing account: SUCCESS");
            System.out.println("Attempting to deposit ₹100.0 on closed account");
            operationalCurrent.deposit(100.0);
            System.out.println("Depositing ₹100.0 - UNEXPECTED SUCCESS");
        } catch (AccountException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            try {
                CurrentAccount operationalCurrent = new CurrentAccount(1006, "Diana Prince", 35, 3000.0);
                operationalCurrent.closeAccount();
                operationalCurrent.reopenAccount();
                System.out.println("Reopening account: SUCCESS");
                operationalCurrent.deposit(100.0);
                System.out.println("Depositing ₹100.0 after reopen: SUCCESS");
                System.out.println("Balance after deposit: ₹" + operationalCurrent.getBalance());
            } catch (AccountException | IllegalStateException e2) {
                System.out.println("EXCEPTION: " + e2.getMessage());
            }
        }

        System.out.println(">>> Test 9: All Accounts Summary");
        System.out.println(formatAccount(savings));
        System.out.println(formatAccount(current));
        System.out.println(formatAccount(accounts[2]));
        System.out.println(formatAccount(accounts[3]));
        System.out.println();
        System.out.println(formatAccountWithoutPin(new SavingsAccount(1005, "Charlie Green", 40, 2200.0)));
        System.out.println(formatAccountWithoutPin(new CurrentAccount(1006, "Diana Prince", 35, 3100.0)));

        finish();
    }

    private static String formatAccount(Account account) {
        return "Account #" + account.getAccountNumber() + " | " + account.getName()
                + " (" + account.getAge() + " yrs) | " + account.getAccountType() + " | ₹"
                + account.getBalance() + " | " + account.getStatus() + " | PIN: "
                + (account.hasPin() ? "Yes" : "No");
    }

    private static String formatAccountWithoutPin(Account account) {
        return "Account #" + account.getAccountNumber() + " | " + account.getName()
                + " (" + account.getAge() + " yrs) | " + account.getAccountType() + " | ₹"
                + account.getBalance() + " | " + account.getStatus();
    }

    private static String formatAccountWithType(Account account) {
        return formatAccountWithoutPin(account) + ", Type: " + account.getAccountType()
                + ", Min Balance: ₹" + account.getMinimumBalance();
    }

    private static void finish() {
        System.out.println("============================================================");
        System.out.println("TEST COMPLETED!");
        System.out.println("============================================================");
    }
}
