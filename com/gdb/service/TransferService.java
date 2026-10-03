package com.gdb.service;

import com.gdb.domain.Account;
import com.gdb.domain.IAccount;
import com.gdb.exceptions.AccountException;
import com.gdb.exceptions.InactiveAccountException;
import com.gdb.exceptions.InsufficientBalanceException;
import com.gdb.exceptions.InvalidPinException;

public class TransferService {
    public void transfer(IAccount from, IAccount to, double amount, String pin) throws AccountException {
        if (from == null || to == null) {
            throw new AccountException("Source and destination accounts are required");
        }

        if (!isActive(from) || !isActive(to)) {
            throw new InactiveAccountException("Both accounts must be active to transfer funds");
        }

        Account source = toConcreteAccount(from);
        int numericPin = parsePin(pin);
        if (!source.verifyPin(numericPin)) {
            throw new InvalidPinException("Incorrect PIN");
        }

        if (!source.canWithdraw(amount)) {
            throw new InsufficientBalanceException("Insufficient balance for transfer of Rs. " + amount);
        }

        source.resetDailyTransferIfNeeded();
        if (!source.canTransfer(amount)) {
            double remaining = source.getRemainingDailyTransferLimit();
            throw new AccountException("Daily transfer limit exceeded. Remaining today: Rs. " + remaining);
        }

        from.withdraw(amount, pin);
        to.deposit(amount);
        source.updateDailyTransferTotal(amount);
    }

    private boolean isActive(IAccount account) {
        return account.getStatus() != null && "ACTIVE".equalsIgnoreCase(account.getStatus());
    }

    private Account toConcreteAccount(IAccount account) throws AccountException {
        if (!(account instanceof Account)) {
            throw new AccountException("Unsupported account implementation for transfer");
        }
        return (Account) account;
    }

    private int parsePin(String pin) throws InvalidPinException {
        try {
            return Integer.parseInt(pin);
        } catch (NumberFormatException ex) {
            throw new InvalidPinException("Incorrect PIN");
        }
    }
}
