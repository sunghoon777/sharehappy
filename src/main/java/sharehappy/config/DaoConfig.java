package sharehappy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"sharehappy.dao"})
public class DaoConfig {

}
