package ru.akirakozov.sd.refactoring.printer;

import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

public class HtmlPrinter implements ResultPrinter {
    private ServletResponse response;

    public HtmlPrinter(ServletResponse response) {
        this.response = response;
        response.setContentType("text/html");
    }

    private void println(String text) throws IOException {
        response.getWriter().println(text);
    }

    private void printToBody(String text) throws IOException {
        println("<html><body>");
        println(text);
        println("</body></html>");
    }

    @Override
    public void printAddResult() throws IOException {
        println("OK");
    }

    @Override
    public void printGetResult(List<Product> data) throws IOException {
        StringBuilder items = new StringBuilder();
        for (Product p : data) {
            items.append(p.getName()).append("\t").append(p.getPrice()).append("</br>");
        }
        printToBody(items.toString());
    }

    @Override
    public void printMinResult(Product product) throws IOException {
        printToBody("<h1>Product with min price: </h1>" +
                "</br>" + product.getName() + "\t" + product.getPrice() + "</br>");
    }

    @Override
    public void printMaxResult(Product product) throws IOException {
        printToBody("<h1>Product with max price: </h1>" +
                "</br>" + product.getName() + "\t" + product.getPrice() + "</br>");
    }

    @Override
    public void printSumResult(int sum) throws IOException {
        printToBody("Summary price: " + sum);
    }

    @Override
    public void printCountResult(int count) throws IOException {
        printToBody("Number of products: " + count);
    }

    @Override
    public void printUnknownResult(String command) throws IOException {
        println("Unknown command: " + command);
    }
}
