package com.shopcalculator.ui;

import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class ShopCalculatorPanel extends PluginPanel {
    private final NumberFormat numberFormat;
    private final ShopCalculatorCalculate Results = new ShopCalculatorCalculate();

    public ShopCalculatorPanel() {
        setLayout(new GridBagLayout());

        // Create a NumberFormat instance with US locale to use comma as thousands separator
        numberFormat = NumberFormat.getNumberInstance(Locale.US);

        // Create and configure input fields
        JTextField itemValueField = new JTextField("0",10);
        JTextField shopIncreaseField = new JTextField("1",10);
        JTextField totalAmountField = new JTextField("0", 10);
        JTextField amountPerWorldField = new JTextField("10", 10);

        // Set document filters for the text fields to allow integers only
        ((AbstractDocument) itemValueField.getDocument()).setDocumentFilter(new NumericFilter());
        ((AbstractDocument) shopIncreaseField.getDocument()).setDocumentFilter(new NumericFilter());
        ((AbstractDocument) totalAmountField.getDocument()).setDocumentFilter(new NumericFilter());
        ((AbstractDocument) amountPerWorldField.getDocument()).setDocumentFilter(new NumericFilter());

        // Create result labels
        JLabel avgCostPerItemLabel = new JLabel();
        JLabel worldHopsNeededLabel = new JLabel();
        JLabel totalCostLabel = new JLabel();

        // Create the "Calculate" button and add a listener to handle the click event
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Results.calculateResults(itemValueField.getText(),
                                         shopIncreaseField.getText(),
                                         totalAmountField.getText(),
                                         amountPerWorldField.getText());

                // Get values from the calculator to the results labels
                avgCostPerItemLabel.setText(formatNumber(Results.values.get("avgCostPerItem")) + " gp");
                worldHopsNeededLabel.setText(formatNumber(Results.values.get("worldHopsNeeded")));
                totalCostLabel.setText(formatNumber(Results.values.get("totalCost")) + " gp");
            }
        });

        // Create a helper method to add components with specified coordinates
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        // Create components aligned to left
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(new JLabel("Base item value"), 0, 0, gbc);
        addComponent(new JLabel("Change per (%)"), 0, 1, gbc);
        addComponent(new JLabel("Amount to buy"), 2, 0, gbc);
        addComponent(new JLabel("Amount per world"), 2, 1, gbc);
        addComponent(new JLabel("Average per item"),9,0, gbc);
        addComponent(new JLabel("Total world hops"),11,0, gbc);
        addComponent(new JLabel("Total cost"),13,0, gbc);

        // Create components aligned to center
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(itemValueField, 1, 0, gbc);
        addComponent(shopIncreaseField, 1, 1, gbc);
        addComponent(totalAmountField, 3, 0, gbc);
        addComponent(amountPerWorldField, 3, 1, gbc);
        addComponent(avgCostPerItemLabel, 9, 1, gbc);
        addComponent(worldHopsNeededLabel, 11,1, gbc);
        addComponent(totalCostLabel, 13, 1, gbc);

        // Create components that span 2 columns
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addComponent(calculateButton, 7, 0, gbc);
        addComponent(new JSeparator(),8, 0, gbc);
        addComponent(new JSeparator(),10, 0, gbc);
        addComponent(new JSeparator(),12, 0, gbc);
        addComponent(new JSeparator(),14, 0, gbc);

    }

    private void addComponent(Component component, int gridY, int gridX, GridBagConstraints gbc) {
        gbc.gridy = gridY;
        gbc.gridx = gridX;
        add(component, gbc);
    }

    private String formatNumber(double number) {
        return numberFormat.format(number);
    }

    // Filtering for text fields to allow only numerics in them
    private static class NumericFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder sb = new StringBuilder(string);
            for (int i = sb.length() - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                if (!Character.isDigit(c) && c != '.') {
                    sb.deleteCharAt(i);
                }
            }
            super.insertString(fb, offset, sb.toString(), attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            StringBuilder sb = new StringBuilder(text);
            for (int i = sb.length() - 1; i >= 0; i--) {
                char c = sb.charAt(i);
                if (!Character.isDigit(c) && c != '.') {
                    sb.deleteCharAt(i);
                }
            }
            super.replace(fb, offset, length, sb.toString(), attrs);
        }
    }
}

