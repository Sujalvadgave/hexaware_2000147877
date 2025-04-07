
package entity;

public class ZeroBalanceAccount extends Account {
    public ZeroBalanceAccount(Customer customer) {
        super(customer, "ZeroBalance", 0f); // Always starts with zero balance
    }

    @Override
    public String toString() {
        return "ZeroBalanceAccount{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", customer=" + customer.getFirstName() + " " + customer.getLastName() +
                '}';
    }
}