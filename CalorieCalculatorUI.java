import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalorieCalculatorUI extends JFrame {
    private JTextField genderField, ageField, weightField, heightField, activityField;
    private JLabel resultLabel;

    public CalorieCalculatorUI() {
        setTitle("Calorie Calculator");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create main panel with GridLayout for simplicity
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(45, 45, 45));

        // Add input fields and labels
        panel.add(createLabel("Gender (M/F):"));
        genderField = createTextField(panel);

        panel.add(createLabel("Age:"));
        ageField = createTextField(panel);

        panel.add(createLabel("Weight (kg):"));
        weightField = createTextField(panel);

        panel.add(createLabel("Height (cm):"));
        heightField = createTextField(panel);

        panel.add(createLabel("Activity (sedentary/moderate/active):"));
        activityField = createTextField(panel);

        // Add result label and button
        resultLabel = createLabel("Your result will appear here.");
        panel.add(resultLabel);
        JButton calculateButton = createButton("Calculate");
        calculateButton.addActionListener(this::calculateCalories);
        panel.add(calculateButton);

        add(panel); // Add panel to frame
    }

    // Action handler for the calculate button
    private void calculateCalories(ActionEvent e) {
        try {
            String gender = genderField.getText().trim().toUpperCase();
            int age = Integer.parseInt(ageField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            double height = Double.parseDouble(heightField.getText().trim());
            String activity = activityField.getText().trim().toLowerCase();

            double bmr = gender.equals("M")
                    ? 88.362 + 13.397 * weight + 4.799 * height - 5.677 * age
                    : 447.593 + 9.247 * weight + 3.098 * height - 4.330 * age;

            double multiplier = switch (activity) {
                case "sedentary" -> 1.2;
                case "moderate" -> 1.55;
                case "active" -> 1.725;
                default -> throw new IllegalArgumentException("Invalid activity");
            };

            resultLabel.setText(String.format("<html>BMR: %.0f cal/day<br>Daily Needs: %.0f cal/day</html>",
                    bmr, bmr * multiplier));
        } catch (Exception ex) {
            resultLabel.setText("Invalid input. Please try again.");
        }
    }

    // Helper to create styled JLabel
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    // Helper to create styled JTextField
    private JTextField createTextField(JPanel panel) {
        JTextField textField = new JTextField();
        textField.setBackground(new Color(60, 63, 65));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(textField);
        return textField;
    }

    // Helper to create styled JButton
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(75, 110, 175));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalorieCalculatorUI().setVisible(true));
    }
}
