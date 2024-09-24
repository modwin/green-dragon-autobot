package org.bunke.gui;

import org.bunke.util.cfg.Config;

import javax.swing.*;
import java.awt.*;

public class GreenDragonBotGUI {

    private static boolean isInitialized = false; // Static flag to check initialization
    private final JFrame frame;
    private final JComboBox<String> potionComboBox;
    private final JTextField potionAmountField;
    private final JComboBox<String> foodComboBox;
    private final JTextField foodAmountField;
    private final JComboBox<String> attackStyleComboBox;

    // Constructor for GUI
    public GreenDragonBotGUI() {
        if (isInitialized) {
            throw new IllegalStateException("The GUI has already been initialized.");
        }

        frame = new JFrame("Green Dragon Bot Config");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout Setup
        frame.setLayout(new GridLayout(6, 2));

        // Potion Setup
        JLabel potionLabel = new JLabel("Choose Potion:");
        String[] potions = {"Strength", "Defense", "Magic"};
        potionComboBox = new JComboBox<>(potions);
        potionAmountField = new JTextField("0");

        // Food Setup
        JLabel foodLabel = new JLabel("Choose Food:");
        String[] foods = {"Lobster", "Shark", "Swordfish", "Monkfish", "Bass"};
        foodComboBox = new JComboBox<>(foods);
        foodAmountField = new JTextField("0");

        // Combat Style Setup
        JLabel combatStyleLabel = new JLabel("Choose Attack Style:");
        String[] combatStyles = {"Attack", "Strength", "Shared","Defense"};
        attackStyleComboBox = new JComboBox<>(combatStyles);

        // Confirm Button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            // Set values in the Config singleton
            Config.INSTANCE.setPotion((String) potionComboBox.getSelectedItem());
            Config.INSTANCE.setPotionAmount(Integer.parseInt(potionAmountField.getText()));
            Config.INSTANCE.setFood((String) foodComboBox.getSelectedItem());
            Config.INSTANCE.setFoodAmount(Integer.parseInt(foodAmountField.getText()));
            Config.INSTANCE.setAttackStyle((String) attackStyleComboBox.getSelectedItem());

            JOptionPane.showMessageDialog(frame, "Settings Saved Successfully!");
        });

        // Add Components to Frame
        frame.add(potionLabel);
        frame.add(potionComboBox);
        frame.add(new JLabel("Potion Amount:"));
        frame.add(potionAmountField);
        frame.add(foodLabel);
        frame.add(foodComboBox);
        frame.add(new JLabel("Food Amount:"));
        frame.add(foodAmountField);
        frame.add(combatStyleLabel);
        frame.add(attackStyleComboBox);
        frame.add(confirmButton);


        frame.setVisible(true);
        isInitialized = true;  // Set initialization flag
    }

    // Static method to check if the GUI has been initialized
    public static boolean isInitialized() {
        isInitialized = Config.INSTANCE.getFood() != null;
        return isInitialized;
    }

    public void close(){
        frame.dispose();;
    }

    // Main method to launch GUI
    public void initialize() {
        if (!isInitialized) {
            SwingUtilities.invokeLater(GreenDragonBotGUI::new);
        } else {
            System.out.println("The GUI is already initialized.");
        }
    }
}
