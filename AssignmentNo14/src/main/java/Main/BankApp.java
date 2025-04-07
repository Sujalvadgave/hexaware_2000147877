package Main;

import dao.BankServiceProviderImpl;
import entity.Account;
import entity.Customer;
import exception.InsufficientFundException;
import exception.InvalidAccountException;
import exception.OverDraftLimitExceededException;

import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankServiceProviderImpl bank = new BankServiceProviderImpl("Hexaware", "Kolhapur");

        while (true) {
            System.out.println("\n=== Welcome to Hexaware Bank ===");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Get Balance");
            System.out.println("5. Transfer");
            System.out.println("6. Get Account Details");
            System.out.println("7. List All Accounts");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Customer Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Mobile: ");
                        String mobile = sc.nextLine();

                        Customer customer = new Customer(name, email, mobile);

                        System.out.print("Enter Account Type (savings/current/zerobalance): ");
                        String accType = sc.nextLine().toLowerCase();
                        System.out.print("Enter Initial Balance: ");
                        float balance = sc.nextFloat();

                        bank.create_account(customer, accType, balance);
                        System.out.println("Account created successfully!");
                        break;

                    case 2:
                        System.out.print("Enter Account Number: ");
                        long accNoD = sc.nextLong();
                        System.out.print("Enter Amount to Deposit: ");
                        float amtD = sc.nextFloat();
                        System.out.println("New Balance: " + bank.deposit(accNoD, amtD));
                        break;

                    case 3:
                        System.out.print("Enter Account Number: ");
                        long accNoW = sc.nextLong();
                        System.out.print("Enter Amount to Withdraw: ");
                        float amtW = sc.nextFloat();
                        System.out.println("Remaining Balance: " + bank.withdraw(accNoW, amtW));
                        break;

                    case 4:
                        System.out.print("Enter Account Number: ");
                        long accNoB = sc.nextLong();
                        System.out.println("Balance: " + bank.getAccountBalance(accNoB));
                        break;

                    case 5:
                        System.out.print("Enter Sender Account Number: ");
                        long from = sc.nextLong();
                        System.out.print("Enter Receiver Account Number: ");
                        long to = sc.nextLong();
                        System.out.print("Enter Amount to Transfer: ");
                        float amtT = sc.nextFloat();
                        bank.transfer(from, to, amtT);
                        System.out.println("Transfer Successful!");
                        break;

                    case 6:
                        System.out.print("Enter Account Number: ");
                        long accNo = sc.nextLong();
                        Account found = bank.getAccountDetails(accNo);
                      
                      

                    case 7:
                        bank.listAccounts();
                        break;

                    case 8:
                        System.out.println("Thank you for using Hexaware Bank. Goodbye!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InsufficientFundException | InvalidAccountException |
                     OverDraftLimitExceededException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        }
    }
}
