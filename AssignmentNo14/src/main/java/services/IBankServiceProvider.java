package services;

import entity.Account;
import entity.Customer;
import java.util.List;

public interface IBankServiceProvider {
    /**
     * Creates a new bank account for a customer with the specified initial balance.
     * @param customer The customer who owns the account.
     * @param accNo Ignored (account number is database-generated).
     * @param accType The type of account (Savings, Current, ZeroBalance).
     * @param balance The initial balance.
     */
    void createAccount(Customer customer, long accNo, String accType, float balance);

    /**
     * Lists all accounts in the bank.
     * @return A list of all accounts.
     */
    List<Account> listAccounts();

    /**
     * Retrieves account and customer details for a given account number.
     * @param accountNumber The account number to query.
     * @return The Account object with customer details.
     */
    Account getAccountDetails(long accountNumber);

    /**
     * Calculates and adds interest to all savings accounts based on their balance and rate.
     */
    void calculateInterest();
}