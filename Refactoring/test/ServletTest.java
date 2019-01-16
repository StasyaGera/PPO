import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

public class ServletTest {
    private final String HOST = "localhost";
    private final static int PORT = 8081;
    private final static String DB_HOST = "jdbc:sqlite:test.db";
    private final static String TABLE_NAME = "PRODUCT";

    private static Server server;

    private static void executeSQLUpdate(String query) throws SQLException {
        try (Connection dbConnection = DriverManager.getConnection(DB_HOST);
             Statement stmt = dbConnection.createStatement()) {
            stmt.executeUpdate(query);
        }
    }

    private static void setupDB() throws SQLException {
        executeSQLUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    private static void setupServer() throws Exception {
        server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet()),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet()),"/query");

        server.start();
    }

    @BeforeAll
    public static void setup() throws Exception {
        setupDB();
        setupServer();
    }

    private static void shutdownDB() throws SQLException {
        executeSQLUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    private static void shutdownServer() throws Exception {
        server.stop();
    }

    @AfterAll
    public static void shutdown() throws Exception {
        shutdownDB();
        shutdownServer();
    }

    @AfterEach
    void clearDB() throws SQLException {
        executeSQLUpdate("DELETE FROM " + TABLE_NAME);
    }

    private String executeServerRequest(String request) {
        String query = "http://" + HOST + ":" + PORT + "/" + request;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(query).openStream()))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine);
                response.append("\n");
            }
            return response.toString();
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException received: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Wrong data from server: " + e.getMessage());
        }
        return null;
    }

    private void fillDB(Integer... values) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + "(NAME, PRICE) VALUES (?, ?)";
        try (Connection dbConnection = DriverManager.getConnection(DB_HOST);
             PreparedStatement prepStmt = dbConnection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                prepStmt.setString(1, "product" + i);
                prepStmt.setInt(2, values[i]);
                prepStmt.addBatch();
            }
            prepStmt.executeBatch();
        }
    }

    private String parseHTMLBody(String response) {
        return Jsoup.parseBodyFragment(response).body().text().trim();
    }

    @Test
    public void add() throws SQLException {
        int testSize = 3;
        for (int i = 0; i < testSize; i++) {
            Assert.assertEquals("OK\n", executeServerRequest("add-product?name=product" + i + "&price=1"));
        }
        try (Connection dbConnection = DriverManager.getConnection(DB_HOST);
             Statement stmt = dbConnection.createStatement();
             ResultSet results = stmt.executeQuery("SELECT NAME, PRICE FROM " + TABLE_NAME)) {
            int i = 0;
            while (results.next()) {
                Assert.assertEquals("product" + i, results.getString("NAME"));
                Assert.assertEquals(1, results.getInt("PRICE"));
                Assert.assertTrue(i < testSize);
                i++;
            }
        }
    }

    @Test
    public void get() throws SQLException {
        Integer[] values = {1, 1, 1};

        StringBuilder expected = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            expected.append("product").append(i).append(" ").append(values[i]).append(" ");
        }
        fillDB(values);
        Assert.assertEquals(expected.toString().trim(),
                parseHTMLBody(executeServerRequest("get-products")));
    }

    @Test
    public void queryMax() throws SQLException {
        Integer[] values = {10, 30, 20};

        int i_max = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[i_max]) {
                i_max = i;
            }
        }
        fillDB(values);
        Assert.assertEquals("Product with max price: product" + i_max + " " + values[i_max],
                parseHTMLBody(executeServerRequest("query?command=max")));
    }

    @Test
    public void queryMin() throws SQLException {
        Integer[] values = {30, 10, 5, 20};

        int i_min = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < values[i_min]) {
                i_min = i;
            }
        }
        fillDB(values);
        Assert.assertEquals("Product with min price: product" + i_min + " " + values[i_min],
                parseHTMLBody(executeServerRequest("query?command=min")));
    }

    @Test
    public void querySum() throws SQLException {
        Integer[] values = {2, 3, 5};

        int sum = 0;
        for (Integer value : values) {
            sum += value;
        }
        fillDB(values);
        Assert.assertEquals("Summary price: " + sum,
                parseHTMLBody(executeServerRequest("query?command=sum")));
    }

    @Test
    public void queryCount() throws SQLException {
        Integer[] values = {1, 1, 1};
        fillDB(values);
        Assert.assertEquals("Number of products: " + values.length,
                parseHTMLBody(executeServerRequest("query?command=count")));
    }

    @Test
    public void queryUnknown() {
        String command = "other";
        Assert.assertEquals("Unknown command: " + command,
                parseHTMLBody(executeServerRequest("query?command=" + command)));
    }
}
