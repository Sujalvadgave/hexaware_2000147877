package dao;

import entity.*;
import util.DBUtil;
import exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankRepositoryImpl implements dao.IBankRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/HMBanks";
    private static final String USER = "root";       // Replace with your MySQL username
    private static final String PASSWORD = "Hexaware@12345"; // Replace with your MySQL password

    @Override
    public void createAccount(Customer customer, long accNo, String accType, float balance) {
        String sql = "INSERT INTO Accounts (customer_id, account_type, balance) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getDBConn();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, customer.getCustomerId());
            stmt.setString(2, accType);
            stmt.setFloat(3, balance);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                // Account number is auto-generated by the database
                System.out.println("Account created with ID: " + rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating account: " + e.getMessage());
        }
    }

    @Override
    public List<Account> listAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT a.*, c.* FROM Accounts a JOIN Customers c ON a.customer_id = c.customer_id";
        try (Connection conn = DBUtil.getDBConn();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(createAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing accounts: " + e.getMessage());
        }
        return accounts;
    }

    @Override
    public float getAccountBalance(long accountNumber) {
        String sql = "SELECT balance FROM Accounts WHERE account_id = ?";
        try (Connection conn = DBUtil.getDBConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("balance");
            }
            throw new InvalidAccountException("Account not found: " + accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting balance: " + e.getMessage());
        }
    }

    @Override
    public float deposit(long accountNumber, float amount) {
        float currentBalance = getAccountBalance(accountNumber);
        float newBalance = currentBalance + amount;
        updateBalance(accountNumber, newBalance);
        recordTransaction(accountNumber, "Deposit", amount);
        return newBalance;
    }

    @Override
    public float withdraw(long accountNumber, float amount) {
        float balance = getAccountBalance(accountNumber);
        Account account = getAccountDetails(accountNumber);

        if (account instanceof SavingsAccount && (balance - amount) < 500) {
            throw new InsufficientFundException("Cannot withdraw below minimum balance of 500");
        }
        if (account instanceof CurrentAccount) {
            float overdraftLimit = ((CurrentAccount) account).getOverdraftLimit();
            if ((balance - amount) < -overdraftLimit) {
                throw new OverDraftLimitExceededException("Overdraft limit exceeded");
            }
        } else if (balance < amount) {
            throw new InsufficientFundException("Insufficient funds");
        }

        float newBalance = balance - amount;
        updateBalance(accountNumber, newBalance);
        recordTransaction(accountNumber, "Withdrawal", amount);
        return newBalance;
    }

    @Override
    public void transfer(long fromAccountNumber, long toAccountNumber, float amount) {
        // Validate both accounts exist
        getAccountDetails(fromAccountNumber);
        getAccountDetails(toAccountNumber);

        withdraw(fromAccountNumber, amount);
        deposit(toAccountNumber, amount);
        recordTransaction(fromAccountNumber, "Transfer Out", amount);
        recordTransaction(toAccountNumber, "Transfer In", amount);
    }

    @Override
    public Account getAccountDetails(long accountNumber) {
        String sql = "SELECT a.*, c.* FROM Accounts a JOIN Customers c ON a.customer_id = c.customer_id WHERE a.account_id = ?";
        try (Connection conn = DBUtil.getDBConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createAccountFromResultSet(rs);
            }
            throw new InvalidAccountException("Account not found: " + accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting account details: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ?";
        try (Connection conn = DBUtil.getDBConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountNumber);
            stmt.setDate(2, new java.sql.Date(fromDate.getTime()));
            stmt.setDate(3, new java.sql.Date(toDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction(
                    getAccountDetails(rs.getLong("account_id")),
                    rs.getString("description"),
                    rs.getString("transaction_type"),
                    rs.getFloat("amount")
                );
                t.setTransactionId(rs.getLong("transaction_id"));
                transactions.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting transactions: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public void calculateInterest() {
        String sql = "UPDATE Accounts SET balance = balance + (balance * 0.045) WHERE account_type = 'Savings'";
        try (Connection conn = DBUtil.getDBConn();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating interest: " + e.getMessage());
        }
    }

    private void updateBalance(long accountNumber, float newBalance) {
        String sql = "UPDATE Accounts SET balance = ? WHERE account_id = ?";
        try (Connection conn = DBUtil.getDBConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, newBalance);
            stmt.setLong(2, accountNumber);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new InvalidAccountException("Account not found: " + accountNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating balance: " + e.getMessage());
        }
    }

    private void recordTransaction(long accountNumber, String type, float amount) {
        String sql = "INSERT INTO Transactions (account_id, transaction_type, amount, transaction_date, description) " +
                    "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getDBConn();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountNumber);
            stmt.setString(2, type);
            stmt.setFloat(3, amount);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.setString(5, type + " of " + amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error recording transaction: " + e.getMessage());
        }
    }

    private Account createAccountFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer(
            rs.getLong("customer_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("phone_number"),
            rs.getString("address")
        );

        String accType = rs.getString("account_type");
        float balance = rs.getFloat("balance");

        Account account;
        switch (accType) {
            case "Savings":
                account = new SavingsAccount(customer, balance);
                break;
            case "Current":
                account = new CurrentAccount(customer, balance);
                break;
            case "ZeroBalance":
                account = new ZeroBalanceAccount(customer);
                break;
            default:
                throw new IllegalStateException("Unknown account type: " + accType);
        }

        // Set the account number from the database
        try {
            java.lang.reflect.Field field = Account.class.getDeclaredField("accountNumber");
            field.setAccessible(true);
            field.set(account, rs.getLong("account_id"));
        } catch (Exception e) {
            throw new RuntimeException("Error setting account number: " + e.getMessage());
        }

        return account;
    }
}