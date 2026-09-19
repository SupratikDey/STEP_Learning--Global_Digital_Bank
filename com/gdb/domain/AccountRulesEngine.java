package com.gdb.domain;

public final class AccountRulesEngine {
    private AccountRulesEngine() {
    }

    public static double getSavingsMinBalance(int tenureYears) {
        switch (tenureYears) {
            case 0:
            case 2:
                return 500.0;
            case 4:
            case 6:
                return 1000.0;
            default:
                throw new IllegalArgumentException("Unsupported savings tenure: " + tenureYears);
        }
    }

    public static double getSavingsInterestRate(int tenureYears) {
        switch (tenureYears) {
            case 0:
                return 4.0;
            case 2:
                return 4.5;
            case 4:
                return 5.0;
            case 6:
                return 5.5;
            default:
                throw new IllegalArgumentException("Unsupported savings tenure: " + tenureYears);
        }
    }
}