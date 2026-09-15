package polycabhavells;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SurveyFrame extends JFrame {

    JTextField nameField;
    JTextField ageField;

    JComboBox<String> genderBox;
    JComboBox<String> brandBox;
    JComboBox<String> productBox;

    JComboBox<Integer> qualityBox;
    JComboBox<Integer> priceBox;
    JComboBox<Integer> durabilityBox;
    JComboBox<Integer> availabilityBox;
    JComboBox<Integer> serviceBox;
    JComboBox<Integer> overallBox;

    public SurveyFrame() {

        setTitle("Customer Satisfaction Survey");
        setSize(550, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(
                new GridLayout(13, 2, 8, 8)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        nameField = new JTextField();
        ageField = new JTextField();

        genderBox = new JComboBox<>(
                new String[]{"Male", "Female", "Other"}
        );

        brandBox = new JComboBox<>(
                new String[]{"Polycab", "Havells"}
        );

        productBox = new JComboBox<>(
                new String[]{
                        "Wires",
                        "Switches",
                        "Fans",
                        "Lighting",
                        "Other"
                }
        );

        qualityBox = createRatingBox();
        priceBox = createRatingBox();
        durabilityBox = createRatingBox();
        availabilityBox = createRatingBox();
        serviceBox = createRatingBox();
        overallBox = createRatingBox();

        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        panel.add(new JLabel("Gender:"));
        panel.add(genderBox);

        panel.add(new JLabel("Brand:"));
        panel.add(brandBox);

        panel.add(new JLabel("Product:"));
        panel.add(productBox);

        panel.add(new JLabel("Quality (1-5):"));
        panel.add(qualityBox);

        panel.add(new JLabel("Price (1-5):"));
        panel.add(priceBox);

        panel.add(new JLabel("Durability (1-5):"));
        panel.add(durabilityBox);

        panel.add(new JLabel("Availability (1-5):"));
        panel.add(availabilityBox);

        panel.add(new JLabel("Service (1-5):"));
        panel.add(serviceBox);

        panel.add(new JLabel("Overall Satisfaction (1-5):"));
        panel.add(overallBox);

        JButton saveButton = new JButton("Save Survey");
        JButton clearButton = new JButton("Clear");

        panel.add(saveButton);
        panel.add(clearButton);

        add(panel);

        saveButton.addActionListener(
                e -> saveSurvey()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        setVisible(true);
    }

    private JComboBox<Integer> createRatingBox() {

        return new JComboBox<>(
                new Integer[]{1, 2, 3, 4, 5}
        );
    }

    private void saveSurvey() {

        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter customer name and age."
            );

            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageText);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number."
            );

            return;
        }

        String sql =
                "INSERT INTO customer_satisfaction " +
                "(customer_name, age, gender, brand, product, " +
                "quality, price, durability, availability, service, " +
                "overall_satisfaction) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3,
                    genderBox.getSelectedItem().toString());
            pst.setString(4,
                    brandBox.getSelectedItem().toString());
            pst.setString(5,
                    productBox.getSelectedItem().toString());

            pst.setInt(6,
                    (Integer) qualityBox.getSelectedItem());

            pst.setInt(7,
                    (Integer) priceBox.getSelectedItem());

            pst.setInt(8,
                    (Integer) durabilityBox.getSelectedItem());

            pst.setInt(9,
                    (Integer) availabilityBox.getSelectedItem());

            pst.setInt(10,
                    (Integer) serviceBox.getSelectedItem());

            pst.setInt(11,
                    (Integer) overallBox.getSelectedItem());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Survey saved successfully!"
            );

            clearForm();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }

    private void clearForm() {

        nameField.setText("");
        ageField.setText("");

        genderBox.setSelectedIndex(0);
        brandBox.setSelectedIndex(0);
        productBox.setSelectedIndex(0);

        qualityBox.setSelectedIndex(0);
        priceBox.setSelectedIndex(0);
        durabilityBox.setSelectedIndex(0);
        availabilityBox.setSelectedIndex(0);
        serviceBox.setSelectedIndex(0);
        overallBox.setSelectedIndex(0);
    }
}