package com.BSpackage;

public class CheckDriver {

    public static void main(String[] args) {
        checkDriver();
    }

    public static void checkDriver() {
        try {
            // Attempt to load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC driver.");
            e.printStackTrace();
        }
    }
}

