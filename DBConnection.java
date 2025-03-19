package com.example.project3;

import java.sql.*;// The java.sql package provides the classes and interfaces necessary for interacting with relational databases.
// It includes functionality for establishing database connections, executing SQL queries, and processing the results.


// Define the DBConnection class
public class DBConnection {

    Connection connection;  // Declare a Connection object to manage the database connection

    // Define the database URL
    private String url = "jdbc:mysql://127.0.0.1:3306/hotelmanagmentsystem70";

    // Define the database username
    private String username = "root";

    // Define the database password
    private String password = "123456789";

    // Method to establish and return the database connection
    public Connection getConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            // Print any exception that occurs while loading the driver
            e.printStackTrace();
        }
        try {
            // Attempt to establish a connection to the database using the provided URL, username, and password
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // Print any SQL exception that occurs while establishing the connection
            e.printStackTrace();// The printStackTrace() method is used to print the details of the throwable (exception or error) to the standard error stream.
        }
        // Return the established database connection
        return connection;
    }
}