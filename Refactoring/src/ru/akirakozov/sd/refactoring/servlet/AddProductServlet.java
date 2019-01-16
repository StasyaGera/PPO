package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dbAdapter.DBAdapter;
import ru.akirakozov.sd.refactoring.dbAdapter.SQLAdapter;
import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    private DBAdapter adapter = new SQLAdapter("jdbc:sqlite:test.db", "PRODUCT");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        try {
            adapter.insert(new Product(name, price));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new HtmlPrinter(response).printAddResult();
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
