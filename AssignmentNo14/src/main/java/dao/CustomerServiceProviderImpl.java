package dao;
import entity.Transaction;
import services.ICustomerServiceProvider;
import entity.Account;
import java.util.Date;
import java.util.List;

public class CustomerServiceProviderImpl implements ICustomerServiceProvider {
    private final BankRepositoryImpl repository;

    public CustomerServiceProviderImpl() {
        this.repository = new BankRepositoryImpl();
    }

    @Override
    public float getAccountBalance(long accountNumber) {
        return repository.getAccountBalance(accountNumber);
    }

    @Override
    public float deposit(long accountNumber, float amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        return repository.deposit(accountNumber, amount);
    }

    @Override
    public float withdraw(long accountNumber, float amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        return repository.withdraw(accountNumber, amount);
    }

    @Override
    public void transfer(long fromAccountNumber, long toAccountNumber, float amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (fromAccountNumber == toAccountNumber) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        repository.transfer(fromAccountNumber, toAccountNumber, amount);
    }

    @Override
    public Account getAccountDetails(long accountNumber) {
        return repository.getAccountDetails(accountNumber);
    }

    @Override
    public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("From date must be before to date");
        }
        return repository.getTransactions(accountNumber, fromDate, toDate);
    }
}