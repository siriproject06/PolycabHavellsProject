package polycabhavells;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ComparisonFrame extends JFrame {

    JTextArea resultArea;

    public ComparisonFrame() {

        setTitle("Polycab vs Havells Comparison");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        resultArea = new JTextArea();

        resultArea.setEditable(false);
        resultArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        add(scrollPane);

        loadComparison();

        setVisible(true);
    }

    private void loadComparison() {

        String sql =
                "SELECT brand, " +
                "COUNT(*) AS total, " +
                "AVG(quality) AS quality, " +
                "AVG(price) AS price, " +
                "AVG(durability) AS durability, " +
                "AVG(availability) AS availability, " +
                "AVG(service) AS service, " +
                "AVG(overall_satisfaction) AS overall " +
                "FROM customer_satisfaction " +
                "GROUP BY brand";

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                Statement stmt =
                        con.createStatement();

                ResultSet rs =
                        stmt.executeQuery(sql)
        ) {

            double polycabOverall = 0;
            double havellsOverall = 0;

            boolean polycabFound = false;
            boolean havellsFound = false;

            resultArea.append(
                    "CUSTOMER SATISFACTION COMPARISON\n"
            );

            resultArea.append(
                    "====================================\n\n"
            );

            while (rs.next()) {

                String brand =
                        rs.getString("brand");

                int total =
                        rs.getInt("total");

                double quality =
                        rs.getDouble("quality");

                double price =
                        rs.getDouble("price");

                double durability =
                        rs.getDouble("durability");

                double availability =
                        rs.getDouble("availability");

                double service =
                        rs.getDouble("service");

                double overall =
                        rs.getDouble("overall");

                resultArea.append(
                        "Brand: " + brand + "\n"
                );

                resultArea.append(
                        "Total Responses: " + total + "\n"
                );

                resultArea.append(
                        String.format(
                                "Quality: %.2f%n",
                                quality
                        )
                );

                resultArea.append(
                        String.format(
                                "Price: %.2f%n",
                                price
                        )
                );

                resultArea.append(
                        String.format(
                                "Durability: %.2f%n",
                                durability
                        )
                );

                resultArea.append(
                        String.format(
                                "Availability: %.2f%n",
                                availability
                        )
                );

                resultArea.append(
                        String.format(
                                "Service: %.2f%n",
                                service
                        )
                );

                resultArea.append(
                        String.format(
                                "Overall Satisfaction: %.2f%n%n",
                                overall
                        )
                );

                if (brand.equalsIgnoreCase("Polycab")) {

                    polycabOverall = overall;
                    polycabFound = true;

                } else if (
                        brand.equalsIgnoreCase("Havells")
                ) {

                    havellsOverall = overall;
                    havellsFound = true;
                }
            }

            resultArea.append(
                    "====================================\n"
            );

            if (polycabFound && havellsFound) {

                if (polycabOverall > havellsOverall) {

                    resultArea.append(
                            String.format(
                                    "Higher Overall Satisfaction: " +
                                    "Polycab (%.2f)%n",
                                    polycabOverall
                            )
                    );

                } else if (
                        havellsOverall > polycabOverall
                ) {

                    resultArea.append(
                            String.format(
                                    "Higher Overall Satisfaction: " +
                                    "Havells (%.2f)%n",
                                    havellsOverall
                            )
                    );

                } else {

                    resultArea.append(
                            "Both brands have equal " +
                            "overall satisfaction.\n"
                    );
                }

            } else {

                resultArea.append(
                        "Add responses for both Polycab " +
                        "and Havells to compare them.\n"
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }
}