package com.shopcalculator.ui;

import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.floor;
import java.text.NumberFormat;
import java.util.Locale;

public class ShopCalculatorPanel extends PluginPanel {
    private final NumberFormat numberFormat;
    private final JTextField itemValueField;
    private final JTextField shopIncreaseField;
    private final JTextField totalTransactionsField;
    private final JTextField itemsPerHopField;
    private final JLabel avgCostPerItemLabel;
    private final JLabel hopsNeededLabel;
    private final JLabel totalCostPerHopLabel;
    private final JLabel totalCostLabel;


    public ShopCalculatorPanel() {
        setLayout(new GridBagLayout());

        // Create a NumberFormat instance with US locale to use comma as thousands separator
        numberFormat = NumberFormat.getNumberInstance(Locale.US);

        // Create and configure input fields
        itemValueField = new JTextField(10);
        itemValueField.setText("0");
        shopIncreaseField = new JTextField(10);
        shopIncreaseField.setText("1");
        totalTransactionsField = new JTextField(10);
        totalTransactionsField.setText("0");
        itemsPerHopField = new JTextField(10);
        itemsPerHopField.setText("10");

        // Set document filters for the text fields to allow integers only
        ((AbstractDocument) itemValueField.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
        ((AbstractDocument) shopIncreaseField.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
        ((AbstractDocument) totalTransactionsField.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
        ((AbstractDocument) itemsPerHopField.getDocument()).setDocumentFilter(new IntegerOnlyFilter());

        // Create result labels
        avgCostPerItemLabel = new JLabel();
        hopsNeededLabel = new JLabel();
        totalCostPerHopLabel = new JLabel();
        totalCostLabel = new JLabel();

        // Create the "Calculate" button and add a listener to handle the click event
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });

        // Create a helper method to add components with specified coordinates
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // Create components aligned to left
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(new JLabel("Item value"), 0, 0, gbc);
        addComponent(new JLabel("Change per (%)"), 0, 1, gbc);
        addComponent(new JLabel("Total to buy/sell"), 2, 0, gbc);
        addComponent(new JLabel("Items per Hop"), 2, 1, gbc);
        addComponent(new JLabel("Average/item"),9,0, gbc);
        addComponent(new JLabel("Hops needed"),11,0, gbc);
        addComponent(new JLabel("Total/hop"),13,0, gbc);
        addComponent(new JLabel("Total"),15,0, gbc);

        // Create components aligned to center
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(itemValueField, 1, 0, gbc);
        addComponent(shopIncreaseField, 1, 1, gbc);
        addComponent(totalTransactionsField, 3, 0, gbc);
        addComponent(itemsPerHopField, 3, 1, gbc);
        addComponent(avgCostPerItemLabel, 9, 1, gbc);
        addComponent(hopsNeededLabel, 11,1, gbc);
        addComponent(totalCostPerHopLabel, 13, 1, gbc);
        addComponent(totalCostLabel, 15, 1, gbc);

        // Create components that span 2 columns
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(calculateButton, 7, 0, gbc);
        addComponent(new JSeparator(),8, 0, gbc);
        addComponent(new JSeparator(),10, 0, gbc);
        addComponent(new JSeparator(),12, 0, gbc);
        addComponent(new JSeparator(),14, 0, gbc);
        addComponent(new JSeparator(),16, 0, gbc);
    }

    private void addComponent(Component component, int gridY, int gridX, GridBagConstraints gbc) {
        gbc.gridy = gridY;
        gbc.gridx = gridX;
        add(component, gbc);
    }

    // Calculations are done here
    private void calculateResults() {
        int item_value = parseIntegerOrDefault(itemValueField.getText());
        double shop_increase = parseDoubleOrDefault(shopIncreaseField.getText()) * 0.01;
        int total_transactions = parseIntegerOrDefault(totalTransactionsField.getText());
        int items_per_hop = parseIntegerOrDefault(itemsPerHopField.getText());
        double total_cost_per_hop = 0;

        // Calculation for total cost per hop for buying
        for (int i = 0;i<items_per_hop;i++) {
            total_cost_per_hop += floor(item_value + (i * item_value * shop_increase));
        }

        avgCostPerItemLabel.setText(formatNumber(total_cost_per_hop / items_per_hop) + " gp");
        hopsNeededLabel.setText(String.valueOf(formatNumber((double) total_transactions / items_per_hop)));
        totalCostPerHopLabel.setText(formatNumber(total_cost_per_hop) + " gp");
        totalCostLabel.setText(formatNumber((double) total_transactions / items_per_hop * total_cost_per_hop) + " gp");
    }

    private String formatNumber(double number) {
        return numberFormat.format(number);
    }

    // Handles filtering for integers only
    private static class IntegerOnlyFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder sb = new StringBuilder(string);
            for (int i = sb.length() - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                if (!Character.isDigit(c)) {
                    sb.deleteCharAt(i);
                }
            }
            super.insertString(fb, offset, sb.toString(), attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(text);
            for (int i = sb.length() - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                if (!Character.isDigit(c)) {
                    sb.deleteCharAt(i);
                }
            }
            super.replace(fb, offset, length, sb.toString(), attrs);
        }
    }

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

