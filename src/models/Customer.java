package models;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}

