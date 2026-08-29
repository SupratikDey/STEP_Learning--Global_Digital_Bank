class Account{
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;

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
        if(amount<=0){
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount){
        if(amount<=0 || this.balance<amount ){
            return false;
        }
        this.balance -= amount;
        return true;
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

}