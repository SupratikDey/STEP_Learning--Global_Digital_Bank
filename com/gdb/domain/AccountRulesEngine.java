package com.gdb.domain;

import java.util.HashMap;
import java.util.Map;

public final class AccountRulesEngine {
    private static final String SAVINGS_FILE = "savings.properties";
    private static final String CURRENT_FILE = "current.properties";
    private static final String FIXED_DEPOSIT_FILE = "fixeddeposit.properties";
    private static final String SALARY_FILE = "salary.properties";

    // Caches the loaders (not the rules), so each file is read once, on first use.
    private static final Map<String, AccountRulesPropertiesLoader> LOADERS = new HashMap<>();

    private AccountRulesEngine() {
    }

    // ===== Savings =====
    public static double getSavingsMinBalance(int tenureYears) {
        return requireDouble(SAVINGS_FILE, "savings.minBalance." + savingsBucket(tenureYears));
    }

    public static double getSavingsInterestRate(int tenureYears) {
        return requireDouble(SAVINGS_FILE, "savings.interestRate." + savingsBucket(tenureYears));
    }

    // ===== Current =====
    public static double getCurrentMinBalance() {
        return requireDouble(CURRENT_FILE, "current.minBalance");
    }

    public static double getCurrentOverdraftLimit() {
        return requireDouble(CURRENT_FILE, "current.overdraftLimit");
    }

    // ===== Fixed deposit =====
    public static int getFixedDepositTermMonths() {
        return (int) requireDouble(FIXED_DEPOSIT_FILE, "fixedDeposit.termMonths");
    }

    public static double getFixedDepositInterestRate() {
        return requireDouble(FIXED_DEPOSIT_FILE, "fixedDeposit.interestRate");
    }

    // ===== Salary =====
    public static double getSalaryMinBalance() {
        return requireDouble(SALARY_FILE, "salary.minBalance");
    }

    public static String getSalaryDefaultEmployer() {
        return loader(SALARY_FILE).getProperty("salary.defaultEmployer", "Unknown");
    }

    // ===== Helpers =====
    private static String savingsBucket(int tenureYears) {
        switch (tenureYears) {
            case 0:
                return "new";
            case 2:
                return "standard";
            case 4:
                return "premium";
            case 6:
                return "privilege";
            default:
                throw new IllegalArgumentException("Unsupported savings tenure: " + tenureYears);
        }
    }

    private static double requireDouble(String fileName, String key) {
        AccountRulesPropertiesLoader loader = loader(fileName);
        double value = loader.getDouble(key, Double.NaN);
        if (Double.isNaN(value)) {
            throw new IllegalStateException(
                    "Missing or invalid rule '" + key + "' in " + loader.getSource());
        }
        return value;
    }

    private static synchronized AccountRulesPropertiesLoader loader(String fileName) {
        AccountRulesPropertiesLoader loader = LOADERS.get(fileName);
        if (loader == null) {
            loader = new AccountRulesPropertiesLoader(fileName);
            LOADERS.put(fileName, loader);
        }
        return loader;
    }
}