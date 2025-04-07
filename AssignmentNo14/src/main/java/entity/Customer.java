package entity;

import java.util.regex.Pattern;

public class Customer {
    private long customerId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String address;

    // Default constructor
    public Customer() {}

    // Parameterized constructor with validation
    public Customer(long customerId, String firstName, String lastName,
                    String emailAddress, String phoneNumber, String address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        setEmailAddress(emailAddress); // Validate email
        setPhoneNumber(phoneNumber);   // Validate phone
        this.address = address;
    }

    public Customer(String name, String email, String mobile) {
    }

    // Getters and Setters with validation
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (emailAddress == null || !Pattern.matches(emailRegex, emailAddress)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be 10 digits");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}