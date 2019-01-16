package dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import model.Product;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


public class TaskListJdbcListDao extends JdbcDaoSupport implements TaskListDao {

    public TaskListJdbcListDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public int addProduct(Product product) {
        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (?, ?)";
        return getJdbcTemplate().update(sql, new Object[] { product.getName(), product.getPrice()});
    }

    @Override
    public List<Product> getProducts() {
        String sql = "SELECT * FROM PRODUCT";
        return getProductsByRequest(sql);
    }

    @Override
    public Optional<Product> getProductWithMaxPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        return getProductsByRequest(sql).stream().findFirst();
    }

    @Override
    public Optional<Product> getProductWithMinPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        return getProductsByRequest(sql).stream().findFirst();

    }

    private List<Product> getProductsByRequest(String sql) {
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Product.class));
    }

}
