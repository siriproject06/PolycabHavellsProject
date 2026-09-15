package polycabhavells;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewResponsesFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewResponsesFrame(String searchName) {

        setTitle("Customer Responses");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "ID",
                "Name",
                "Age",
                "Gender",
                "Brand",
                "Product",
                "Quality",
                "Price",
                "Durability",
                "Availability",
                "Service",
                "Overall"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        JScrollPane scrollPane =
                new JScrollPane(table);

        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.addActionListener(
                e -> loadData("")
        );

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData(searchName);

        setVisible(true);
    }

    private void loadData(String searchName) {

        model.setRowCount(0);

        String sql;

        if (searchName == null || searchName.isEmpty()) {

            sql =
                    "SELECT * FROM customer_satisfaction " +
                    "ORDER BY id DESC";

        } else {

            sql =
                    "SELECT * FROM customer_satisfaction " +
                    "WHERE customer_name LIKE ? " +
                    "ORDER BY id DESC";
        }

        try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            if (searchName != null && !searchName.isEmpty()) {

                pst.setString(
                        1,
                        "%" + searchName + "%"
                );
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Object[] row = {

                        rs.getInt("id"),

                        rs.getString("customer_name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("brand"),

                        rs.getString("product"),

                        rs.getInt("quality"),

                        rs.getInt("price"),

                        rs.getInt("durability"),

                        rs.getInt("availability"),

                        rs.getInt("service"),

                        rs.getInt("overall_satisfaction")
                };

                model.addRow(row);
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }
}