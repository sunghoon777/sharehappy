package sharehappy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"sharehappy.service.DonationPostCategory"})
public class ServiceConfig {
}
