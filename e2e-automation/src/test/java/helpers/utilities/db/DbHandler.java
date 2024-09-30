package helpers.utilities.db;

import helpers.utilities.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbHandler.class);
    private static final ConfigProperties cf = new ConfigProperties();
    private static Connection connection;

    public static Connection connectToDB() throws SQLException {
        String host = cf.getProperty("host");
        String port = cf.getProperty("port");
        String dbName = cf.getProperty("dbName");
        String username = cf.getProperty("username");
        String password = cf.getProperty("password");
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;

        try {
            connection = DriverManager.getConnection(url, username, password);
            LOGGER.info("Connected to the PostgreSQL database successfully.");
            return connection;
        } catch (SQLException e) {
            LOGGER.error("Connection failed: " + e.getMessage());
            throw e;
        }
    }

    public static void switchSchema(String schemaName) throws SQLException {
        String query = "SET search_path TO " + schemaName;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            LOGGER.info("Switched to schema: " + schemaName);
        } catch (SQLException e) {
            LOGGER.error("Failed to switch schema: " + e.getMessage());
            throw e;
        }
    }

    // Method to close the database connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Connection closed.");
            } catch (SQLException e) {
                LOGGER.error("Failed to close connection: " + e.getMessage());
            }
        }
    }
}
