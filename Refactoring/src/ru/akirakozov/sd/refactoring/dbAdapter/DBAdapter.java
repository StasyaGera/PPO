package ru.akirakozov.sd.refactoring.dbAdapter;

import ru.akirakozov.sd.refactoring.Product;

import java.util.List;

public interface DBAdapter {
    void createTable() throws Exception;
    void insert(Product product) throws Exception;
    List<Product> readAll() throws Exception;
    Product getMax() throws Exception;
    Product getMin() throws Exception;
    int getSum() throws Exception;
    int getCount() throws Exception;
}
