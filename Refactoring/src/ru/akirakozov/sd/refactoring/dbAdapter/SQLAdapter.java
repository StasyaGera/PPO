package ru.akirakozov.sd.refactoring.dbAdapter;

import ru.akirakozov.sd.refactoring.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLAdapter implements DBAdapter {
    private String host;
    private String tableName;

    public SQLAdapter(String host, String tableName) {
        this.host = host;
        this.tableName = tableName;
    }


    @Override
    public void createTable() throws Exception {
        try (Connection c = DriverManager.getConnection(host);
             Statement stmt = c.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)");
        }
    }

    @Override
    public void insert(Product product) throws SQLException {
        try (Connection c = DriverManager.getConnection(host);
             Statement stmt = c.createStatement()) {
            stmt.executeUpdate("INSERT INTO " + tableName +
                    " (NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
        }
    }

    @Override
    public List<Product> readAll() throws SQLException {
        List<Product> result = new ArrayList<>();
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
             Statement stmt = c.createStatement();
             ResultSet data = stmt.executeQuery("SELECT * FROM " + tableName)) {
            while (data.next()) {
                result.add(new Product(data.getString("name"), data.getInt("price")));
            }
        }
        return result;
    }

    @Override
    public Product getMax() throws SQLException {
        try (Connection c = DriverManager.getConnection(host);
             Statement stmt = c.createStatement();
             ResultSet data = stmt.executeQuery("SELECT * FROM " + tableName + " ORDER BY PRICE DESC LIMIT 1")) {
            if (data.next()) {
                return new Product(data.getString("name"), data.getInt("price"));
            }
        }
        return null;
    }

    @Override
    public Product getMin() throws SQLException {
        try (Connection c = DriverManager.getConnection(host);
             Statement stmt = c.createStatement();
             ResultSet data = stmt.executeQuery("SELECT * FROM " + tableName + " ORDER BY PRICE LIMIT 1")) {
            if (data.next()) {
                return new Product(data.getString("name"), data.getInt("price"));
            }
        }
        return null;
    }

    @Override
    public int getSum() throws SQLException {
        try (Connection c = DriverManager.getConnection(host);
             Statement stmt = c.createStatement();
             ResultSet data = stmt.executeQuery("SELECT SUM(price) FROM " + tableName)) {
            if (data.next()) {
                return data.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public int getCount() throws SQLException {
        try (Connection c = DriverManager.getConnection(host);
             Statement stmt = c.createStatement();
             ResultSet data = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            if (data.next()) {
                return data.getInt(1);
            }
        }
        return 0;
    }
}
