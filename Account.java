class Account{
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    public Account(int accountNumber,String name,int age,int initialBalance,String accountType){
        this.accountNumber = accountNumber;
        this.name = name;
        if(age<18){
            this.age = 18;
        }
        this.age = (age<18)?18:age;
        this.accountType = (accountType.equals("Savings")||accountType.equals("Current"))? accountType:"Savings";

        if(this.accountType=="Savings"){
            this.balance = (initialBalance<500)?500:initialBalance;
        }else{
            this.balance = (initialBalance<1000)?1000:initialBalance;
        }
        this.status = "Active";
    }

    public boolean deposit(double amount){
        if(amount<=0 || this.status.equals("Inactive")){
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount , int pin){
        if(amount<=0 || this.balance<amount || this.status.equals("Inactive")){
            return false;
        }
        if(verifyPin(pin)){
            double remain_value = this.balance-amount;
            double min_value = this.accountType.equals("Savings")?500:1000;
            if(remain_value<min_value){
                return false;
            }
            this.balance = remain_value;
            return true;            
        }
        return false;    
          
    }

    public int getAccountNumber(){
        return this.accountNumber;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public double getBalance(){
        return this.balance;
    }

    public String getAccountType(){
        return this.accountType;
    }

    public String getStatus(){
        return this.status;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public Boolean closeAccount(){
        if(this.status.equals("Inactive")){
            return false;
        }
        this.status = "Inactive";
        return true;
    }

    public Boolean reopenAccount(){
        if(this.status.equals("Active")){
            return false;
        }
        this.status = "Active";
        return true;
    }

    public boolean setPin(Integer pin){
        if(pin == null || (pin<=9999 && pin>=1000)){
            this.pin = pin;
            return true;
        }
        return false;
    }

    public boolean hasPin(){
        if(this.pin==null){
            return false;
        }
        return true;
    }

    public boolean verifyPin(int pin){
        if(hasPin()){
            if(pin==this.pin){
                return true;
            }
        }
        return false;
    }
}