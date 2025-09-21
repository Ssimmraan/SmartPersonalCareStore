package com.personalcare.services;

import com.personalcare.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private static final String DB_URL = "jdbc:sqlite:SmartStore.db";

    public DatabaseService() {
        createTables();
        insertSampleData();
    }

    private void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL," +
                    "role TEXT NOT NULL)";

            String productsTable = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "category TEXT," +
                    "price REAL," +
                    "quantity INTEGER," +
                    "imagePath TEXT," +
                    "rating REAL)";

            stmt.execute(usersTable);
            stmt.execute(productsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSampleData() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String insertUser = "INSERT OR IGNORE INTO users(username, password, role) VALUES(?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertUser)) {
                ps.setString(1, "admin");
                ps.setString(2, "admin123");
                ps.setString(3, "admin");
                ps.executeUpdate();

                ps.setString(1, "customer");
                ps.setString(2, "cust123");
                ps.setString(3, "customer");
                ps.executeUpdate();
            }

            String insertProduct = "INSERT OR IGNORE INTO products(name, category, price, quantity, imagePath, rating) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertProduct)) {
                ps.setString(1, "Herbal Shampoo");
                ps.setString(2, "Shampoo");
                ps.setDouble(3, 199.99);
                ps.setInt(4, 50);
                ps.setString(5, "/images/shampoo1.jpg");
                ps.setDouble(6, 4.5);
                ps.executeUpdate();

                ps.setString(1, "Aloe Vera Conditioner");
                ps.setString(2, "Conditioner");
                ps.setDouble(3, 249.99);
                ps.setInt(4, 30);
                ps.setString(5, "/images/conditioner1.jpg");
                ps.setDouble(6, 4.7);
                ps.executeUpdate();

                ps.setString(1, "Vitamin Hair Oil");
                ps.setString(2, "Hair Oil");
                ps.setDouble(3, 299.99);
                ps.setInt(4, 20);
                ps.setString(5, "/images/oil1.jpg");
                ps.setDouble(6, 4.6);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category"),
                        rs.getString("imagePath"),
                        rs.getDouble("rating")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String verifyUserReturnRole(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("role");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
