public class TestAccount {
    public static void main(String [] args){
        Account account1 = new Account(101,"Rahul",21,5000,"Savings");
        Account account2 = new Account(102,"Priya",22,10000,"Current");

        // TEST DEPOSIT
        System.out.println("\nDEPOSIT TESTS");

        if(account1.deposit(1000)){
            System.out.println("Deposit of 1000 was successful");
        }else{
            System.out.println("Deposit fail");
        }

        if(account2.deposit(-1000)){
            System.out.println("Deposit of 1000 was successful");
        }else{
            System.out.println("Deposit fail");
        }

        // Test withdrawal

        System.out.println("\nWITHDRAWAL TESTS");

        if (account1.withdraw(100)) {
            System.out.println("Withdrawal of 1000 successful.");
        } else {
            System.out.println("Withdrawal failed.");
        }

        // Insufficient balance
        if (account1.withdraw(100000)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal rejected - insufficient balance.");
        }

        // Displaying details of accounts

        System.out.println("\nACCOUNT INFORMATION");

        System.out.println("-----------------------------");
        System.out.println("Account Number : " + account1.getAccountNumber());
        System.out.println("Name           : " + account1.getName());
        System.out.println("Age            : " + account1.getAge());
        System.out.println("Balance        : " + account1.getBalance());
        System.out.println("Account Type   : " + account1.getAccountType());
        System.out.println("Status         : " + account1.getStatus());

        System.out.println("-----------------------------");
        System.out.println("Account Number : " + account2.getAccountNumber());
        System.out.println("Name           : " + account2.getName());
        System.out.println("Age            : " + account2.getAge());
        System.out.println("Balance        : " + account2.getBalance());
        System.out.println("Account Type   : " + account2.getAccountType());
        System.out.println("Status         : " + account2.getStatus());
        System.out.println("-----------------------------");
    }
}
