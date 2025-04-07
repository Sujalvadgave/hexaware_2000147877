package entity;

public abstract class Account {
    private static long lastAccNo = 1000; // Static variable for unique account numbers
    protected long accountNumber;
    protected String accountType;
    protected float balance;
    protected Customer customer;

    // Default constructor
    public Account() {
        this.accountNumber = ++lastAccNo;
    }

    // Parameterized constructor
    public Account(Customer customer, String accountType, float balance) {
        this.accountNumber = ++lastAccNo;
        this.customer = customer;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Getters and Setters
    public long getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", customer=" + customer.getFirstName() + " " + customer.getLastName() +
                '}';
    }
}