package services;

import entity.Account;
import entity.Transaction;

import java.util.Date;
import java.util.List;


public interface ICustomerServiceProvider {
    /**
     * Retrieves the balance of an account given its account number.
     * @param accountNumber The account number to query.
     * @return The current balance of the account.
     */
    float getAccountBalance(long accountNumber);

    /**
     * Deposits the specified amount into the account.
     * @param accountNumber The account number to deposit into.
     * @param amount The amount to deposit.
     * @return The new balance after deposit.
     */
    float deposit(long accountNumber, float amount);

    /**
     * Withdraws the specified amount from the account.
     * Savings accounts must maintain a minimum balance.
     * Current accounts can use overdraft up to a limit.
     * @param accountNumber The account number to withdraw from.
     * @param amount The amount to withdraw.
     * @return The new balance after withdrawal.
     */
    float withdraw(long accountNumber, float amount);

    /**
     * Transfers money from one account to another.
     * @param fromAccountNumber The source account number.
     * @param toAccountNumber The destination account number.
     * @param amount The amount to transfer.
     */
    void transfer(long fromAccountNumber, long toAccountNumber, float amount);

    /**
     * Retrieves account and customer details for a given account number.
     * @param accountNumber The account number to query.
     * @return The Account object with customer details.
     */
    Account getAccountDetails(long accountNumber);

    /**
     * Retrieves a list of transactions for an account between two dates.
     * @param accountNumber The account number to query.
     * @param fromDate The start date of the transaction period.
     * @param toDate The end date of the transaction period.
     * @return A list of transactions.
     */
    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate);
}