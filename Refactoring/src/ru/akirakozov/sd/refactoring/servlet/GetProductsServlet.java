package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dbAdapter.DBAdapter;
import ru.akirakozov.sd.refactoring.dbAdapter.SQLAdapter;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private DBAdapter adapter = new SQLAdapter("jdbc:sqlite:test.db", "PRODUCT");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            new HtmlPrinter(response).printGetResult(adapter.readAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
