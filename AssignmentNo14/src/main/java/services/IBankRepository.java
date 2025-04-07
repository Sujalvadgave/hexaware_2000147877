package services;

import entity.Account;
import entity.Customer;
import entity.Transaction;

import java.util.Date;
import java.util.List;

public interface IBankRepository {
    /**
     * Creates a new bank account in the database.
     * @param customer The customer who owns the account.
     * @param accNo Ignored (account number is database-generated).
     * @param accType The type of account (Savings, Current, ZeroBalance).
     * @param balance The initial balance.
     */
    void createAccount(Customer customer, long accNo, String accType, float balance);

    /**
     * Lists all accounts from the database.
     * @return A list of all accounts.
     */
    List<Account> listAccounts();

    /**
     * Calculates and updates interest for savings accounts in the database.
     */
    void calculateInterest();

    /**
     * Retrieves the balance of an account from the database.
     * @param accountNumber The account number to query.
     * @return The current balance.
     */
    float getAccountBalance(long accountNumber);

    /**
     * Deposits an amount into an account and updates the database.
     * @param accountNumber The account number to deposit into.
     * @param amount The amount to deposit.
     * @return The new balance.
     */
    float deposit(long accountNumber, float amount);

    /**
     * Withdraws an amount from an account and updates the database.
     * Savings accounts must maintain a minimum balance.
     * Current accounts can use overdraft up to a limit.
     * @param accountNumber The account number to withdraw from.
     * @param amount The amount to withdraw.
     * @return The new balance.
     */
    float withdraw(long accountNumber, float amount);

    /**
     * Transfers money between two accounts and updates the database.
     * @param fromAccountNumber The source account number.
     * @param toAccountNumber The destination account number.
     * @param amount The amount to transfer.
     */
    void transfer(long fromAccountNumber, long toAccountNumber, float amount);

    /**
     * Retrieves account details from the database.
     * @param accountNumber The account number to query.
     * @return The Account object with customer details.
     */
    Account getAccountDetails(long accountNumber);

    /**
     * Retrieves transactions for an account between two dates from the database.
     * @param accountNumber The account number to query.
     * @param fromDate The start date of the transaction period.
     * @param toDate The end date of the transaction period.
     * @return A list of transactions.
     */
    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate);
}