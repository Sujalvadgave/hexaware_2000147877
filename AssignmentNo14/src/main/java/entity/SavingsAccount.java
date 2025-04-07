package entity;

public class SavingsAccount extends Account {
    private float interestRate = 4.5f; // Fixed interest rate as per requirements
    private static final float MIN_BALANCE = 500f;

    public SavingsAccount(Customer customer, float balance) {
        super(customer, "Savings", balance);
        if (balance < MIN_BALANCE) {
            throw new IllegalArgumentException("Savings account requires a minimum balance of " + MIN_BALANCE);
        }
    }

    public float getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                ", customer=" + customer.getFirstName() + " " + customer.getLastName() +
                '}';
    }
}