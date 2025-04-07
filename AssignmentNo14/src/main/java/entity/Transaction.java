
package entity;

import java.util.Date;

public class Transaction {
    private long transactionId;
    private Account account;
    private String description;
    private Date dateTime;
    private String transactionType;
    private float transactionAmount;

    // Constructor
    public Transaction(Account account, String description, String transactionType, float transactionAmount) {
        this.account = account;
        this.description = description;
        this.dateTime = new Date(); // Current date/time
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    // Getters and Setters
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountNumber=" + account.getAccountNumber() +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", transactionType='" + transactionType + '\'' +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}