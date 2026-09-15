package polycabhavells;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/polycab_havells";

    private static final String DB_USER = "root";

    // CHANGE THIS to your MySQL password
    private static final String DB_PASSWORD = "77840608";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }
}