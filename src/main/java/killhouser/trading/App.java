package killhouser.trading;

import killhouser.trading.storage.mysql.repository.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
    repositoryBaseClass = BaseRepositoryImpl.class,
    basePackages = "killhouser.trading.storage.mysql.repository")
@ComponentScan(basePackages = "killhouser.trading")
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
