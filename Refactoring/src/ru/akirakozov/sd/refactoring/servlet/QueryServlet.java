package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dbAdapter.DBAdapter;
import ru.akirakozov.sd.refactoring.dbAdapter.SQLAdapter;
import ru.akirakozov.sd.refactoring.printer.HtmlPrinter;
import ru.akirakozov.sd.refactoring.printer.ResultPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private DBAdapter adapter = new SQLAdapter("jdbc:sqlite:test.db", "PRODUCT");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultPrinter printer = new HtmlPrinter(response);
        String command = request.getParameter("command");
        try {
            switch (command) {
                case "max":
                    printer.printMaxResult(adapter.getMax());
                    break;
                case "min":
                    printer.printMinResult(adapter.getMin());
                    break;
                case "sum":
                    printer.printSumResult(adapter.getSum());
                    break;
                case "count":
                    printer.printCountResult(adapter.getCount());
                    break;
                default:
                    printer.printUnknownResult(command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
