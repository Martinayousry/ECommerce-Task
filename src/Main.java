import models.*;
import services.CheckoutService;

public class Main {
    public static void main(String[] args) {

        Product milk = new Product("Milk Bottle", 50, 10, true, 1000);
        Product chocolate = new Product("Chocolate Box", 160, 5, true, 500);
        Product charger = new Product("Phone Charger", 150, 8, true, 300);

        milk.setExpired(false);
        chocolate.setExpired(false);
        charger.setExpired(false);

        Customer customer = new Customer("Martina", 1000);

        Cart cart = new Cart();
        cart.add(milk, 2);
        cart.add(chocolate, 2);
        cart.add(charger, 1);

        CheckoutService.checkout(customer, cart);
    }
}
