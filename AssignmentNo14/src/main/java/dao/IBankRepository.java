package dao;

import entity.Account;
import entity.Customer;
import entity.Transaction;

import java.util.Date;
import java.util.List;

public interface IBankRepository {
    void createAccount(Customer customer, long accNo, String accType, float balance);

    List<Account> listAccounts();

    float getAccountBalance(long accountNumber);

    float deposit(long accountNumber, float amount);

    float withdraw(long accountNumber, float amount);

    void transfer(long fromAccountNumber, long toAccountNumber, float amount);

    Account getAccountDetails(long accountNumber);

    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate);

    void calculateInterest();
}
