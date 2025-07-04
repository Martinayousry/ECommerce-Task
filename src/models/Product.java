package models;


import interfaces.Shippable;

public class Product implements Shippable {

    private String name;
    private double price;
    private int quantity;
    private boolean expired;
    private boolean shippable;
    private double weight;

    public Product(String name, double price, int quantity, boolean shippable, double weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shippable = shippable;
        this.weight = weight;
        this.expired = false;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public boolean isExpired() { return expired; }
    public void setExpired(boolean expired) { this.expired = expired; }

    public boolean requiresShipping() {
        return shippable;
    }

    public double getWeight() {
        return weight;
    }

    public void reduceQuantity(int q) {
        this.quantity -= q;
    }

    public boolean isAvailable(int requestedQuantity) {
        return !expired && quantity >= requestedQuantity;
    }
}
