public class TestAccountEnhanced {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("ENHANCED ACCOUNT TEST (BOOLEAN RETURNS)");
        System.out.println("============================================================");

        System.out.println();
        System.out.println(">>> Test 1: Valid Account Creation");
        AccountEnhanced acc1 = new AccountEnhanced(1001, "John Doe", 25, 1000.0, "Savings");
        printAccount(acc1);

        System.out.println();
        System.out.println(">>> Test 2: Invalid Age (under 18)");
        System.out.println("Creating account with age 16");
        AccountEnhanced acc2 = new AccountEnhanced(1002, "Young Kid", 16, 500.0, "Savings");
        System.out.println("Age auto-corrected to: " + acc2.getAge());
        printAccount(acc2);

        System.out.println();
        System.out.println(">>> Test 3: Invalid Account Type");
        System.out.println("Creating account with type \"Invalid\"");
        AccountEnhanced acc3 = new AccountEnhanced(1003, "Test User", 25, 500.0, "Invalid");
        System.out.println("Account type defaulted to: " + acc3.getAccType());
        printAccount(acc3);

        System.out.println();
        System.out.println(">>> Test 4: Minimum Balance Enforcement on Creation");
        System.out.println("Creating Savings account with ₹300 (below minimum)");
        AccountEnhanced acc4 = new AccountEnhanced(1004, "Bob Wilson", 25, 300.0, "Savings");
        System.out.println("Balance auto-corrected to minimum: ₹" + acc4.getBalance());
        printAccount(acc4);

        System.out.println();
        System.out.println(">>> Test 5: Withdrawal with Minimum Balance");
        AccountEnhanced acc5 = new AccountEnhanced(1005, "Alice Brown", 30, 1000.0, "Current");
        acc5.setPin(4321);
        System.out.println("Initial: " + formatSummary(acc5));

        if (acc5.withdraw(200.0, 4321)) {
            System.out.println("Withdrawing ₹200.0: SUCCESS");
        } else {
            System.out.println("Withdrawing ₹200.0: FAILED");
        }
        System.out.println("New balance: ₹" + acc5.getBalance());
        System.out.println("After withdrawal: " + formatSummary(acc5));

        if (acc5.withdraw(900.0, 4321)) {
            System.out.println("Withdrawing ₹900.0 (would leave ₹-100): SUCCESS");
        } else {
            System.out.println("Withdrawing ₹900.0 (would leave ₹-100): FAILED (Minimum balance violation)");
        }
        System.out.println("Current balance: ₹" + acc5.getBalance());

        System.out.println();
        System.out.println(">>> Test 6: Account Status Management");
        AccountEnhanced acc6 = new AccountEnhanced(1006, "Charlie Green", 35, 2000.0, "Savings");
        System.out.println("Initial: " + formatSummary(acc6));

        if (acc6.closeAccount()) {
            System.out.println("Closing account: SUCCESS");
        } else {
            System.out.println("Closing account: FAILED");
        }
        System.out.println("After close: " + formatSummary(acc6));

        if (acc6.deposit(500.0)) {
            System.out.println("Depositing ₹500.0 to closed account: SUCCESS");
        } else {
            System.out.println("Depositing ₹500.0 to closed account: FAILED (Account inactive)");
        }

        if (acc6.reopenAccount()) {
            System.out.println("Reopening account: SUCCESS");
        } else {
            System.out.println("Reopening account: FAILED");
        }
        System.out.println("After reopen: " + formatSummary(acc6));

        System.out.println();
        System.out.println(">>> Test 7: PIN Protection");
        AccountEnhanced acc7 = new AccountEnhanced(1007, "Diana Prince", 28, 1500.0, "Savings");

        if (acc7.setPin(1234)) {
            System.out.println("Setting PIN 1234: SUCCESS");
        } else {
            System.out.println("Setting PIN 1234: FAILED");
        }

        if (acc7.withdraw(200.0, 1234)) {
            System.out.println("Withdrawing ₹200.0 with correct PIN (1234): SUCCESS");
        } else {
            System.out.println("Withdrawing ₹200.0 with correct PIN (1234): FAILED");
        }
        System.out.println("New balance: ₹" + acc7.getBalance());

        if (acc7.withdraw(100.0, 9999)) {
            System.out.println("Withdrawing ₹100.0 with incorrect PIN (9999): SUCCESS");
        } else {
            System.out.println("Withdrawing ₹100.0 with incorrect PIN (9999): FAILED (Incorrect PIN)");
        }

        AccountEnhanced accNoPin = new AccountEnhanced(1008, "No PIN User", 30, 1500.0, "Savings");
        if (accNoPin.withdraw(100.0, 1234)) {
            System.out.println("Withdrawing ₹100.0 with PIN not set: SUCCESS");
        } else {
            System.out.println("Withdrawing ₹100.0 with PIN not set: FAILED (PIN not set)");
        }

        System.out.println();
        System.out.println(">>> Test 8: All Accounts Summary");
        printAccount(acc1);
        printAccount(acc2);
        printAccount(acc3);
        printAccount(acc4);
        printAccount(acc5);
        printAccount(acc6);
        printAccount(acc7);

        System.out.println("============================================================");
        System.out.println("ENHANCED TEST COMPLETED!");
        System.out.println("============================================================");
    }

    private static void printAccount(AccountEnhanced account) {
        System.out.println("Account #" + account.getAccountNumber() + " | " + account.getName()
            + " (" + account.getAge() + " yrs) | " + account.getAccType() + " | ₹"
            + account.getBalance() + " | " + account.getStatus() + " | PIN: "
            + (account.hasPin() ? "Yes" : "No"));
    }

    private static String formatSummary(AccountEnhanced account) {
        return "Account #" + account.getAccountNumber() + " | " + account.getName()
            + " (" + account.getAge() + " yrs) | " + account.getAccType() + " | ₹"
            + account.getBalance() + " | " + account.getStatus() + " | PIN: "
            + (account.hasPin() ? "Yes" : "No");
    }
}
