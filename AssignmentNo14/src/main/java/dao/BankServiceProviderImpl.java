package dao;

import services.IBankServiceProvider;
import entity.Account;
import entity.Customer;
import java.util.ArrayList;
import java.util.List;

public class BankServiceProviderImpl extends CustomerServiceProviderImpl implements IBankServiceProvider {
    private List<Account> accountList = new ArrayList<>(); // In-memory list (for reference)
    private final BankRepositoryImpl repository;

    private final String branchName = "Main Branch";
    private final String branchAddress = "123 Banking Street";

    public BankServiceProviderImpl(String hexaware, String kolhapur) {
        this.repository = new BankRepositoryImpl();
    }

    @Override
    public void createAccount(Customer customer, long accNo, String accType, float balance) {
        // Validate initial balance based on account type
        if ("Savings".equals(accType) && balance < 500) {
            throw new IllegalArgumentException("Savings account requires minimum balance of 500");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        repository.createAccount(customer, accNo, accType, balance);
        // Note: accountList could be updated here if we want to maintain an in-memory cache,
        // but since we're using database persistence, we'll rely on listAccounts() instead.
    }

    @Override
    public List<Account> listAccounts() {
        return repository.listAccounts();
    }

    @Override
    public Account getAccountDetails(long accountNumber) {
        return repository.getAccountDetails(accountNumber);
    }

    @Override
    public void calculateInterest() {
        repository.calculateInterest();
    }

    // Getters for branch info (optional)
    public String getBranchName() {
        return branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void create_account(Customer customer, String accType, float balance) {

    }


}