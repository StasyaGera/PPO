package config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import dao.TaskListJdbcListDao;

import javax.sql.DataSource;

public class JdbcDaoContextConfiguration {
    @Bean
    public TaskListJdbcListDao productJdbcDao(DataSource dataSource) {
        return new TaskListJdbcListDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:product.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
