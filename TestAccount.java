import com.gdb.domain.*;

public class TestAccount {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("GLOBAL DIGITAL BANK - ACCOUNT TEST");
        System.out.println("==================================================");

        try {
            System.out.println(">>> 1. Creating Account");
            Account acc1 = new SavingsAccount(1001, "John Doe", 25, 1000.0);
            System.out.println("Account created!");
            printAccount(acc1);

            System.out.println(">>> 2. Deposit Money");
            acc1.deposit(500.0);
            System.out.println("Depositing ₹500.0: SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());

            try {
                acc1.deposit(-100.0);
            } catch (Exception e) {
                System.out.println("Depositing ₹-100.0: FAILED (" + e.getMessage() + ")");
            }

            System.out.println(">>> 3. Withdraw Money");
            acc1.setPin(1234);
            acc1.withdraw(200.0, 1234);
            System.out.println("Withdrawing ₹200.0: SUCCESS");
            System.out.println("New balance: ₹" + acc1.getBalance());

            try {
                acc1.withdraw(2000.0, 1234);
            } catch (Exception e) {
                System.out.println("Withdrawing ₹2000.0: FAILED (" + e.getMessage() + ")");
            }

            System.out.println("Current balance: ₹" + acc1.getBalance());

            System.out.println(">>> 4. Creating Another Account");
            Account acc2 = new CurrentAccount(1002, "Jane Smith", 30, 2000.0);
            printAccount(acc2);

            System.out.println(">>> 5. All Accounts");
            printAccount(acc1);
            printAccount(acc2);

        } catch (Exception e) {
            System.out.println("Unexpected test failure: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("==================================================");
        System.out.println("TEST COMPLETED!");
        System.out.println("==================================================");
    }

    private static void printAccount(Account account) {
        System.out.println("Account #" + account.getAccountNumber() + " | " + account.getName()
            + " (" + account.getAge() + " yrs) | " + account.getAccType() + " | ₹"
            + account.getBalance() + " | " + account.getStatus());
    }
}