package entity;

import entity.Account;
import entity.Customer;

public class CurrentAccount extends Account {
    private float overdraftLimit = 1000f; // Default overdraft limit as per requirements

    public CurrentAccount(Customer customer, float balance) {
        super(customer, "Current", balance);
        if (balance < -overdraftLimit) {
            throw new IllegalArgumentException("Balance cannot exceed overdraft limit of " + overdraftLimit);
        }
    }

    public float getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public String toString() {
        return "CurrentAccount{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", overdraftLimit=" + overdraftLimit +
                ", customer=" + customer.getFirstName() + " " + customer.getLastName() +
                '}';
    }
}