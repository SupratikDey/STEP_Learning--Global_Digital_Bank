package com.gdb.tests;

import java.util.Locale;

import com.gdb.domain.AccountRulesEngine;

public class TestAccountRulesEngineProperties {
    public static void main(String[] args) {
        System.out.println("=== Activity 14: Properties-Driven Rules Engine Test ===");

        int[] tenures = { 0, 2, 4, 6 };
        double[] expectedMinBalances = { 10000.0, 7500.0, 5000.0, 2500.0 };
        double[] expectedRates = { 2.70, 3.00, 3.50, 4.00 };

        for (int i = 0; i < tenures.length; i++) {
            double minimumBalance = AccountRulesEngine.getSavingsMinBalance(tenures[i]);
            double interestRate = AccountRulesEngine.getSavingsInterestRate(tenures[i]);
            System.out.println(String.format(Locale.US,
                    "Tenure %d yrs -> Min Balance: Rs %-7s | Interest: %.2f%%",
                    tenures[i], minimumBalance, interestRate));

            if (minimumBalance != expectedMinBalances[i] || interestRate != expectedRates[i]) {
                System.out.println("MISMATCH for tenure " + tenures[i]
                        + " - check savings.properties");
                System.exit(1);
            }
        }

        try {
            AccountRulesEngine.getSavingsMinBalance(3);
            System.out.println("Unsupported tenure was NOT rejected");
            System.exit(1);
        } catch (IllegalArgumentException expected) {
            // unsupported tenures must still be rejected
        }

        System.out.println("All external properties loaded and verified successfully!");
    }
}
