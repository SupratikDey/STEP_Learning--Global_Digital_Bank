package com.gdb.tests;

import com.gdb.domain.Account;
import com.gdb.domain.AccountFactory;
import com.gdb.domain.IAccount;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.service.TransferService;

public class TestTransfer {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("  ACTIVITY 15 - TRANSFER WITH DAILY LIMITS");
        System.out.println("============================================================");

        TransferService transferService = new TransferService();

        try {
            IAccount from = AccountFactory.createAccount(
                    "SAVINGS", "1001", "Rajesh Sharma", 30, 100000.0, "ACTIVE", null);
            IAccount to = AccountFactory.createAccount(
                    "SAVINGS", "1002", "Priya Patel", 28, 20000.0, "ACTIVE", null);

            Account acc1 = (Account) from;
            Account acc2 = (Account) to;
            acc1.setPin(1234);
            acc1.setTenureYears(0);

            System.out.println("[STEP 9] Account #1001 | " + acc1.getName()
                    + " (" + acc1.getAge() + " yrs, Tenure: " + acc1.getTenureYears() + " yrs)"
                    + " | " + acc1.getAccountType() + " | Rs. " + acc1.getBalance()
                    + " | " + acc1.getStatus());
            System.out.println("[STEP 9] Account #1002 | " + acc2.getName()
                    + " (" + acc2.getAge() + " yrs, Tenure: " + acc2.getTenureYears() + " yrs)"
                    + " | " + acc2.getAccountType() + " | Rs. " + acc2.getBalance()
                    + " | " + acc2.getStatus());

            transferService.transfer(from, to, 5000.0, "1234");
            System.out.println("[STEP 10] Transfer Rs. 5,000: SUCCESS | acc1 = Rs. "
                    + acc1.getBalance() + " | acc2 = Rs. " + acc2.getBalance());

            try {
                transferService.transfer(from, to, 100000.0, "1234");
            } catch (InsufficientBalanceException e) {
                System.out.println("[STEP 11] Caught InsufficientBalanceException: " + e.getMessage());
            }

            System.out.println("[STEP 12] Daily limit for acc1: Rs. " + acc1.getDailyTransferLimit());
            int transferCount = 0;
            while (true) {
                try {
                    transferService.transfer(from, to, 20000.0, "1234");
                    transferCount++;
                    System.out.println("  Transfer #" + transferCount
                            + " of Rs. 20,000: SUCCESS | used today = Rs. "
                            + acc1.getDailyTransferTotal());
                } catch (AccountException e) {
                    System.out.println("[STEP 12] Caught AccountException: " + e.getMessage());
                    break;
                }
            }

            System.out.println("[STEP 13] Used today: Rs. " + acc1.getDailyTransferTotal()
                    + " | Remaining: Rs. " + acc1.getRemainingDailyTransferLimit());

            IAccount fixedDeposit = AccountFactory.createAccount(
                    "FIXED_DEPOSIT", "2001", "Amit Das", 45, 300000.0, "ACTIVE", null);
            Account fdAcc = (Account) fixedDeposit;
            fdAcc.setPin(4321);
            try {
                transferService.transfer(fixedDeposit, to, 1000.0, "4321");
                System.out.println("[FD CHECK] Unexpected SUCCESS");
            } catch (AccountException e) {
                System.out.println("[FD CHECK] Caught AccountException: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
