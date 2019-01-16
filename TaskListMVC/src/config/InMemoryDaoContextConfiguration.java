package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dao.TaskListDao;
import dao.TaskListInMemoryListDao;

@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskListDao productDao() {
        return new TaskListInMemoryListDao();
    }
}
