package polycabhavells;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {

        setTitle("Polycab & Havells - Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(
                new GridLayout(6, 1, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        );

        JLabel title =
                new JLabel(
                        "Customer Satisfaction System",
                        SwingConstants.CENTER
                );

        JButton surveyButton =
                new JButton("New Customer Survey");

        JButton viewButton =
                new JButton("View Responses");

        JButton searchButton =
                new JButton("Search Responses");

        JButton compareButton =
                new JButton("Compare Polycab & Havells");

        JButton logoutButton =
                new JButton("Logout");

        panel.add(title);
        panel.add(surveyButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(compareButton);
        panel.add(logoutButton);

        add(panel);

        surveyButton.addActionListener(
                e -> new SurveyFrame()
        );

        viewButton.addActionListener(
                e -> new ViewResponsesFrame("")
        );

        searchButton.addActionListener(e -> {

            String name = JOptionPane.showInputDialog(
                    this,
                    "Enter customer name:"
            );

            if (name != null && !name.trim().isEmpty()) {
                new ViewResponsesFrame(name.trim());
            }
        });

        compareButton.addActionListener(
                e -> new ComparisonFrame()
        );

        logoutButton.addActionListener(e -> {

            dispose();

            new LoginFrame();
        });

        setVisible(true);
    }
}