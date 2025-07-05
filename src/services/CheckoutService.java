package services;

import interfaces.Shippable;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    static final double SHIPPING_COST = 30;

    public static void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) throw new IllegalStateException("Cart is empty.");

        double subtotal = 0;
        List<Shippable> toShip = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();

            if (p.isExpired()) throw new IllegalStateException(p.getName() + " is expired.");
            if (item.getQuantity() > p.getQuantity()) throw new IllegalStateException("Stock issue: " + p.getName());

            subtotal += item.getTotalPrice();

            if (p.requiresShipping() && p instanceof Shippable) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    toShip.add(new ShippableItem(p.getName(), p.getWeight()));
                }
            }
        }

        double total = subtotal + (toShip.isEmpty() ? 0 : SHIPPING_COST);
        if (!customer.hasEnoughBalance(total)) throw new IllegalStateException("Insufficient balance.");

        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
        customer.decreaseBalance(total);

        if (!toShip.isEmpty()) ShippingService.ship(toShip);

        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s\t%.0f\n", item.getQuantity(), item.getProduct().getName(), item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal\t%.0f\n", subtotal);
        if (!toShip.isEmpty()) System.out.printf("Shipping\t%.0f\n", SHIPPING_COST);
        System.out.printf("Amount\t\t%.0f\n", total);

        System.out.printf("Customer balance after payment: %.2f\n", customer.getBalance());


        cart.clear();
    }
}