package com.BSpackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderServlet")
public class OrderBackend extends HttpServlet {
  
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/order_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Rootpin";

    static {
        try {
 
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC driver. Check logs for details.", e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String foodItem = req.getParameter("foodItem");

            try {
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                String paymentOption = req.getParameter("paymentOption");

                String insertQuery = "INSERT INTO order_data (food_item, quantity, payment_option) VALUES (?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, foodItem);
                    preparedStatement.setInt(2, quantity);
                    preparedStatement.setString(3, paymentOption);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        resp.getWriter().write("Order data saved successfully.");
                    } else {
                        resp.getWriter().write("Failed to save order data.");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                resp.getWriter().write("Failed to save order data. Check logs for details. Error: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("Failed to establish a database connection. Check logs for details. Error: " + e.getMessage());
        }
    }
}
