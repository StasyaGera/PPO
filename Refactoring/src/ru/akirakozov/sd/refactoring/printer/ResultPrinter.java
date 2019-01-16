package ru.akirakozov.sd.refactoring.printer;

import ru.akirakozov.sd.refactoring.Product;

import java.io.IOException;
import java.util.List;

public interface ResultPrinter {
    void printAddResult() throws IOException;
    void printGetResult(List<Product> data) throws IOException;
    void printMinResult(Product product) throws IOException;
    void printMaxResult(Product product) throws IOException;
    void printSumResult(int sum) throws IOException;
    void printCountResult(int count) throws IOException;
    void printUnknownResult(String command) throws IOException;
}
