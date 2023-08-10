package com.shopcalculator.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class ShopCalculatorCalculate {
    public final Map<String, Double> values = new LinkedHashMap<>();

    public void calculateResults(String item_value, String shop_increase, String total_amount, String amount_per_world) {
        double itemValue = floor(parseDoubleOrDefault(item_value));
        double shopIncrease = parseDoubleOrDefault(shop_increase) * 0.01;
        double totalAmount = parseDoubleOrDefault(total_amount);
        double amountPerWorld = parseDoubleOrDefault(amount_per_world);
        double costPerWorld = 0;

        // Calculation for total cost per hop for buying
        for (int i = 0; i < amountPerWorld; i++) {
            costPerWorld += floor(itemValue + (i * itemValue * shopIncrease));
        }

        values.put("avgCostPerItem", costPerWorld / amountPerWorld);
        values.put("worldHopsNeeded", ceil(totalAmount / amountPerWorld));
        values.put("totalCost", floor(totalAmount / amountPerWorld * costPerWorld));
    }

    // Error handling and double parsing
    private double parseDoubleOrDefault(String text ) {
        if (text.isEmpty()) {
            return 0;
        } else {
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    // Error handling and integer parsing
    private int parseIntegerOrDefault(String text) {
        if (text.isEmpty()) {
            return 0;
        } else {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}
