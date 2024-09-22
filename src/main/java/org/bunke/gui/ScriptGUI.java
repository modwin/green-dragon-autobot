package org.bunke.gui;

import org.bunke.util.cfg.Config;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScriptGUI {

    private final JFrame frame;
    private final JComboBox<String> categoryComboBox;
    private final CardLayout cardLayout;

    public ScriptGUI() {
        frame = new JFrame("Runescape Script Config");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Dropdown to choose between Combat and Skilling
        String[] categories = {"Skilling", "Combat"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.addActionListener(new CategoryActionListener());

        frame.setLayout(new BorderLayout());
        frame.add(categoryComboBox, BorderLayout.NORTH);

        // Panels for Skilling and Combat
        JPanel skillingPanel = createSkillingPanel();
        JPanel combatPanel = createCombatPanel();

        // CardLayout to switch between the two panels
        cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.add(skillingPanel, "Skilling");
        mainPanel.add(combatPanel, "Combat");

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void initialize() {
        new ScriptGUI();
    }

    private JPanel createSkillingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel activityLabel = new JLabel("Choose Activity:");
        String[] activities = {"Woodcutting", "Mining", "Fishing"};
        JComboBox<String> activityComboBox = new JComboBox<>(activities);
        activityComboBox.addActionListener(e -> {
            String activity = (String) activityComboBox.getSelectedItem();
//            Config.INSTANCE.setSkillingActivity(activity);
        });

        JLabel treeLabel = new JLabel("Choose Tree:");
        String[] trees = {"Oak", "Willow", "Yew"};
        JComboBox<String> treeComboBox = new JComboBox<>(trees);
        treeComboBox.addActionListener(e -> {
            String tree = (String) treeComboBox.getSelectedItem();
//            Config.INSTANCE.setTree(tree);
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Skilling settings saved.");
        });

        panel.add(activityLabel);
        panel.add(activityComboBox);
        panel.add(treeLabel);
        panel.add(treeComboBox);
        panel.add(confirmButton);

        return panel;
    }

    private JPanel createCombatPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel npcLabel = new JLabel("Choose NPC:");
        String[] npcs = {"Goblin", "Dragon", "Giant"};
        JComboBox<String> npcComboBox = new JComboBox<>(npcs);
        npcComboBox.addActionListener(e -> {
            String npc = (String) npcComboBox.getSelectedItem();
//            Config.INSTANCE.setNpc(npc);
        });

        JLabel potionLabel = new JLabel("Choose Potion:");
        String[] potions = {"Strength", "Defense", "Magic"};
        JComboBox<String> potionComboBox = new JComboBox<>(potions);
        JTextField potionAmountField = new JTextField();
        potionComboBox.addActionListener(e -> {
            Config.INSTANCE.setPotion((String) potionComboBox.getSelectedItem());
            Config.INSTANCE.setPotionAmount(Integer.parseInt(potionAmountField.getText()));
        });

        JLabel foodLabel = new JLabel("Choose Food:");
        String[] foods = {"Lobster", "Shark", "Swordfish"};
        JComboBox<String> foodComboBox = new JComboBox<>(foods);
        JTextField foodAmountField = new JTextField();
        foodComboBox.addActionListener(e -> {
            Config.INSTANCE.setFood((String) foodComboBox.getSelectedItem());
            Config.INSTANCE.setPotionAmount(Integer.parseInt(foodAmountField.getText()));
        });

        JLabel combatStyleLabel = new JLabel("Combat Style:");
        String[] combatStyles = {"Melee", "Range", "Magic"};
        JComboBox<String> combatStyleComboBox = new JComboBox<>(combatStyles);
        combatStyleComboBox.addActionListener(e -> {
            Config.INSTANCE.setCombatStyle((String) combatStyleComboBox.getSelectedItem());
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Combat settings saved.");
        });

        panel.add(npcLabel);
        panel.add(npcComboBox);
        panel.add(potionLabel);
        panel.add(potionComboBox);
        panel.add(new JLabel("Potion Amount:"));
        panel.add(potionAmountField);
        panel.add(foodLabel);
        panel.add(foodComboBox);
        panel.add(new JLabel("Food Amount:"));
        panel.add(foodAmountField);
        panel.add(combatStyleLabel);
        panel.add(combatStyleComboBox);
        panel.add(confirmButton);

        return panel;
    }

    private class CategoryActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            cardLayout.show(frame.getContentPane(), selectedCategory);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScriptGUI::new);
    }
}
