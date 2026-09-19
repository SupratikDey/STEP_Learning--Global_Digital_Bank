public class AccountEnhanced {
    private int accNumber;
    private String name;
    private int age;
    private double balance;
    private String accType;
    private String status;
    private Integer pin;
    public AccountEnhanced(int accNumber, String name, int age, double initialBalance, String accType) {
        this.accNumber = accNumber;
        this.name = name;
        this.age = (age < 18) ? 18 : age;

        if (accType == null || (!accType.equals("Savings") && !accType.equals("Current"))) {
            this.accType = "Savings";
        } else {
            this.accType = accType;
        }

        double minimumBalance = this.accType.equals("Savings") ? 500 : 1000;
        this.balance = (initialBalance < minimumBalance) ? minimumBalance : initialBalance;
        this.status = "Active";
        this.pin = null;
    }

    public boolean deposit(double depVal) {
        if (!isActive()) {
            return false;
        }
        if (depVal <= 0) {
            return false;
        }
        this.balance += depVal;
        return true;
    }
    public boolean withdraw(double drawVal) {
        if (!isActive()) {
            return false;
        }
        if (drawVal <= 0 || this.balance < drawVal) {
            return false;
        }
        double minimumBalance = getMinimumBalance();
        if ((this.balance - drawVal) < minimumBalance) {
            return false;
        }
        this.balance -= drawVal;
        return true;
    }
    public boolean withdraw(double drawVal, int pin) {
        if (!isActive()) {
            return false;
        }
        if (!verifyPin(pin)) {
            return false;
        }
        return withdraw(drawVal);
    }
    public boolean closeAccount() {
        if ("Inactive".equals(this.status)) {
            return false;
        }
        this.status = "Inactive";
        return true;
    }
    public boolean reopenAccount() {
        if ("Active".equals(this.status)) {
            return false;
        }
        this.status = "Active";
        return true;
    }
    public boolean setPin(int pin) {
        if (pin >= 1000 && pin <= 9999) {
            this.pin = pin;
            return true;
        }
        return false;
    }
    public boolean verifyPin(int pin) {
        return this.pin != null && this.pin.equals(pin);
    }
    public boolean hasPin() {
        return this.pin != null;
    }
    public int getAccountNumber() {
        return this.accNumber;
    }
    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
    public double getBalance() {
        return this.balance;
    }

    public String getAccType() {
        return this.accType;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getPin() {
        return this.pin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = (age < 18) ? 18 : age;
    }

    public boolean isActive() {
        return "Active".equals(this.status);
    }

    private double getMinimumBalance() {
        return this.accType.equals("Savings") ? 500 : 1000;
    }
}
