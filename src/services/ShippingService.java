package services;

import interfaces.Shippable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ShippingService {
    public static void ship(List<Shippable> items) {
        System.out.println("** Shipment notice **");

        Map<String, Integer> countMap = new HashMap<>();
        Map<String, Double> weightMap = new HashMap<>();

        double totalWeight = 0;

        for (Shippable item : items) {
            String name = item.getName();
            double weight = item.getWeight();

            totalWeight += weight;

            countMap.put(name, countMap.getOrDefault(name, 0) + 1);

            weightMap.put(name, weightMap.getOrDefault(name, 0.0) + weight);
        }

        for (String name : countMap.keySet()) {
            int count = countMap.get(name);
            double weight = weightMap.get(name);
            System.out.printf("%dx %s\t%.0fg\n", count, name, weight);
        }

        System.out.printf("Total package weight %.1fkg\n", totalWeight / 1000);
    }
}
