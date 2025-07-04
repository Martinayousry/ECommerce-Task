package services;

import interfaces.Shippable;
import models.Cart;
import models.CartItem;
import models.Customer;
import models.Product;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    static final double ShippingCost = 30;
    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) throw new IllegalStateException("Cart is empty.");

        double subtotal = 0;
        List<Shippable> toShip = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            if (p.isExpired()) throw new IllegalStateException(p.getName() + " is expired.");
            if (item.getQuantity() > p.getQuantity())
                throw new IllegalStateException("Not enough items in stock: " + p.getName());

            subtotal += item.getTotalPrice();

            if (p.requiresShipping() && p instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    toShip.add((Shippable) p);
                }
            }
        }

        double total = subtotal + (toShip.isEmpty() ? 0 :  ShippingCost);
        if (!customer.hasEnoughBalance(total)) throw new IllegalStateException("Insufficient balance.");

        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        customer.decreaseBalance(total);

        if (!toShip.isEmpty()) shipItems(toShip);


        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s\t%.0f\n", item.getQuantity(), item.getProduct().getName(), item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal\t%.0f\n", subtotal);
        if (!toShip.isEmpty()) System.out.printf("Shipping\t%.0f\n",  ShippingCost);
        System.out.printf("Amount\t\t%.0f\n", total);

        cart.clear();


    }

    private static void shipItems(List<Shippable> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (Shippable item : items) {
            System.out.println(item.getName() + "\t" + (int) item.getWeight() + "g");
            totalWeight += item.getWeight();
        }
        System.out.printf("Total package weight %.1fkg\n", totalWeight / 1000);
    }
}
